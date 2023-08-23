package org.maveric.currencyexchange.serviceimpl;

import org.maveric.currencyexchange.entity.Account;
import org.maveric.currencyexchange.entity.Customer;
import org.maveric.currencyexchange.entity.Transaction;
import org.maveric.currencyexchange.exception.*;
import org.maveric.currencyexchange.payload.OrderRequest;
import org.maveric.currencyexchange.payload.OrderResponse;
import org.maveric.currencyexchange.repository.IAccountRepository;
import org.maveric.currencyexchange.repository.ICustomerRepository;
import org.maveric.currencyexchange.repository.ITransactionRepository;
import org.maveric.currencyexchange.service.IExchangeRatesService;
import org.maveric.currencyexchange.service.IOrderService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private ITransactionRepository transactionRepo;
    private ICustomerRepository customerRepo;
    private IAccountRepository accountRepo;
    private IExchangeRatesService exchangeService;
    private ModelMapper mapper;

    public OrderServiceImpl(ITransactionRepository transactionRepo, ICustomerRepository customerRepo, IAccountRepository accountRepo, ModelMapper mapper, IExchangeRatesService exchangeService) {
        this.transactionRepo = transactionRepo;
        this.customerRepo = customerRepo;
        this.accountRepo = accountRepo;
        this.exchangeService = exchangeService;
        this.mapper = mapper;
    }

    private Customer verifyCustomer(long customerId) {
        logger.info("verifying customer");
        return customerRepo.findById(customerId).orElseThrow(
                () -> {
                    logger.error("Customer Not Found");
                    return new CustomerNotFoundException("Customer Not Found");
                }
        );
    }

    @Override
    public List<OrderResponse> fetchAllTransactions(long customerId) {
        verifyCustomer(customerId);

        List<Transaction> transactions = transactionRepo.findByCustomerId(customerId);
        logger.info("Transactions Fetched Successfully");
        return transactions.stream()
                .map(transaction -> mapper.map(transaction, OrderResponse.class))
                .toList();
    }

    private void validateAccounts(Account srcAccount, Account destAccount, List<Account> accounts) {
        if (srcAccount.equals(destAccount)) {
            logger.error("source & destination accounts should be different");
            throw new AccountConflictException("Can't Transfer to the Same Account");
        }
        if (!accounts.contains(srcAccount) || !accounts.contains(destAccount)) {
            logger.error("Account Mis Match");
            throw new AccountMisMatchException("It's Not Your Account");
        }
    }

    private void performTransaction(OrderRequest orderRequest, Account srcAccount, Account destAccount, double destRate) {
        double sufficientAmount = srcAccount.getAmount().doubleValue();
        if (orderRequest.getAmount() > sufficientAmount)
            throw new InsufficientFundsException("Max Amount you can send: " + sufficientAmount);
        else {
            logger.info("Transaction Initiated...");
            srcAccount.setAmount(srcAccount.getAmount().subtract(BigDecimal.valueOf(orderRequest.getAmount())));
            destAccount.setAmount(destAccount.getAmount().add(BigDecimal.valueOf(orderRequest.getAmount() * destRate)));
        }
    }

    @Override
    @Transactional
    public OrderResponse createTransaction(long customerId, OrderRequest orderRequest) {

        Customer customer = verifyCustomer(customerId);

        List<Account> accounts = accountRepo.findByCustomerId(customerId);

        logger.info("creating order");

        Account srcAccount = getAccountOrThrow(orderRequest.getSrcAccount(), "Source");
        Account destAccount = getAccountOrThrow(orderRequest.getDestAccount(), "Destination");

        validateAccounts(srcAccount, destAccount, accounts);

        String srcCurrency = srcAccount.getCurrency().name();
        String destCurrency = destAccount.getCurrency().name();

        double destRate = exchangeService.getLatestExchangeRates(srcCurrency).getRates().get(destCurrency);
        performTransaction(orderRequest, srcAccount, destAccount, destRate);

        Transaction transaction = mapper.map(orderRequest, Transaction.class);
        transaction.setCustomer(customer);
        transaction.setCurrencyPair(srcCurrency + "/" + destCurrency);
        transaction.setRate(destRate);
        transaction = transactionRepo.save(transaction);

        logger.info("order created successfully");

        return mapper.map(transaction, OrderResponse.class);
    }

    private Account getAccountOrThrow(String accountId, String accountType) {
        logger.info("verifying customer account");
        return accountRepo.findByAccountNumber(accountId)
                .orElseThrow(() -> {
                    logger.error("{} Account Not Found", accountType);
                    throw new AccountNotFoundException(accountType + " Account Not Found");
                });
    }
}