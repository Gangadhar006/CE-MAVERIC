package org.maveric.currencyexchange.serviceimpl;

import org.maveric.currencyexchange.entity.Account;
import org.maveric.currencyexchange.entity.Customer;
import org.maveric.currencyexchange.exception.AccountMisMatchException;
import org.maveric.currencyexchange.exception.AccountNotFoundException;
import org.maveric.currencyexchange.exception.CustomerNotFoundException;
import org.maveric.currencyexchange.payload.AccountRequest;
import org.maveric.currencyexchange.payload.AccountResponse;
import org.maveric.currencyexchange.repository.IAccountRepository;
import org.maveric.currencyexchange.repository.ICustomerRepository;
import org.maveric.currencyexchange.service.IAccountService;
import org.maveric.currencyexchange.utils.AccountNumberGenerator;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService {
    public static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private ModelMapper mapper;
    private IAccountRepository accountRepo;
    private ICustomerRepository customerRepo;
    private AccountNumberGenerator accountNumberGenerator;

    public AccountServiceImpl(ModelMapper mapper, IAccountRepository accountRepo, ICustomerRepository customerRepo, AccountNumberGenerator accountNumberGenerator) {
        this.accountNumberGenerator = accountNumberGenerator;
        this.accountRepo = accountRepo;
        this.customerRepo = customerRepo;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public AccountResponse createAccount(long customerId, AccountRequest accountRequest) {
        Customer customer = verfiyCustomer(customerId);
        Account account = mapper.map(accountRequest, Account.class);
        account.setCustomer(customer);
        account = accountRepo.save(account);
        String accountNumber = accountNumberGenerator.generateUniqueAccountNumber(account.getId());
        account.setAccountNumber(accountNumber);
        logger.info("Account created successfully for CUSTOMER");
        return mapper.map(account, AccountResponse.class);
    }


    @Override
    @Transactional
    public AccountResponse updateAccount(long customerId, long accountId, AccountRequest accountRequest) {
        logger.info("Updating account with CUSTOMER");
        verfiyCustomer(customerId);
        return updateAccountUtil(customerId, accountId, accountRequest);
    }

    public AccountResponse updateAccountUtil(long customerId, long accountId, AccountRequest accountRequest) {
        Account account = verifyAccount(accountId, customerId);
        account.setAmount(accountRequest.getAmount());
        account.setAccountType(accountRequest.getAccountType());
        account.setCurrency(accountRequest.getCurrency());
        account = accountRepo.save(account);
        return mapper.map(account, AccountResponse.class);
    }

    @Override
    @Transactional
    public String deleteAccount(long customerId, long accountId) {
        Account account = verifyAccount(accountId, customerId);
        accountRepo.delete(account);
        logger.info("Account deleted successfully");
        return "account deleted successfully";
    }

    @Override
    public List<AccountResponse> findAllAccounts(long customerId) {
        logger.info("Fetching All Customers with CUSTOMER-ID");
        verfiyCustomer(customerId);
        List<Account> accounts = accountRepo.findByCustomerId(customerId);
        return accounts.stream()
                .map(account -> mapper.map(account, AccountResponse.class))
                .toList();
    }

    public Account verifyAccount(long accountId, long customerId) {
        Account account = accountRepo.findById(accountId).orElseThrow(
                () -> {
                    logger.error("Account Not Found");
                    throw new AccountNotFoundException("Account Not Found");
                }
        );

        boolean isAccountOwner = account.getCustomer().getId().equals(customerId);

        if (!isAccountOwner) {
            logger.error("Account Mis Match CUSTOMER");
            throw new AccountMisMatchException("It's Not Your Account");
        }
        return account;
    }

    public Customer verfiyCustomer(long customerId) {
        return customerRepo.findById(customerId).orElseThrow(
                () -> {
                    logger.error("Customer Not Found");
                    throw new CustomerNotFoundException("Customer Not Found");
                }
        );
    }
}