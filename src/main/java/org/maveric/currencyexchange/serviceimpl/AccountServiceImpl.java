package org.maveric.currencyexchange.serviceimpl;

import org.maveric.currencyexchange.dtos.AccountDto;
import org.maveric.currencyexchange.entity.Account;
import org.maveric.currencyexchange.entity.Customer;
import org.maveric.currencyexchange.exception.AccountMisMatchException;
import org.maveric.currencyexchange.exception.AccountNotFoundException;
import org.maveric.currencyexchange.exception.CustomerNotFoundException;
import org.maveric.currencyexchange.repository.IAccountRepository;
import org.maveric.currencyexchange.repository.ICustomerRepository;
import org.maveric.currencyexchange.service.IAccountService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements IAccountService {
    public static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private ModelMapper mapper;
    private IAccountRepository accountRepo;
    private ICustomerRepository customerRepo;

    public AccountServiceImpl(ModelMapper mapper, IAccountRepository accountRepo, ICustomerRepository customerRepo) {
        this.accountRepo = accountRepo;
        this.customerRepo = customerRepo;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public AccountDto createAccount(Long customerId, AccountDto accountDto) {
        Optional<Customer> customer = customerRepo.findById(customerId);

        if (customer.isEmpty()) {
            logger.error("Customer Not Found");
            throw new CustomerNotFoundException("Customer Not Found");
        }

        if (accountDto != null) {
            Account account = mapper.map(accountDto, Account.class);
            account.setCustomer(customer.get());
            account = accountRepo.save(account);
            accountDto = mapper.map(account, AccountDto.class);
            logger.info("Account created successfully for CUSTOMER");
        }

        return accountDto;
    }


    @Override
    @Transactional
    public AccountDto updateAccount(Long customerId, Long accountId, AccountDto accountDto) {
        logger.info("Updating account with CUSTOMER");

        Optional<Customer> customer = customerRepo.findById(customerId);

        if (customer.isPresent())
            accountDto = updateAccountUtil(customerId, accountId, accountDto);
        else {
            logger.error("Customer Not Found");
            throw new CustomerNotFoundException("Customer Not Found");
        }
        return accountDto;
    }

    public AccountDto updateAccountUtil(Long customerId, Long accountId, AccountDto accountDto) {
        if (accountDto == null) {
            return null;
        }

        Optional<Account> account = accountRepo.findById(accountId);

        if (account.isEmpty()) {
            logger.error("Account Not Found");
            throw new AccountNotFoundException("Account Not Found");
        }

        Account accountCopy = account.get();
        boolean isAccountOwner = accountCopy.getCustomer().getId().equals(customerId);

        if (!isAccountOwner) {
            logger.error("Account Mis Match CUSTOMER");
            throw new AccountMisMatchException("It's Not Your Account");
        }

        if (accountDto.getAmount() != null) {
            accountCopy.setAmount(accountDto.getAmount());
        }

        if (accountDto.getActive() != null) {
            accountCopy.setActive(accountDto.getActive());
        }

        if (accountDto.getAccountType() != null) {
            accountCopy.setAccountType(accountDto.getAccountType());
        }

        if (accountDto.getCurrency() != null) {
            accountCopy.setCurrency(accountDto.getCurrency());
        }

        accountCopy = accountRepo.save(accountCopy);
        accountDto = mapper.map(accountCopy, AccountDto.class);

        return accountDto;
    }


    @Override
    @Transactional
    public String deleteAccount(Long customerId, Long accountId) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> {
                    logger.error("Account Not Found");
                    return new AccountNotFoundException("Account Not Found");
                });

        if (!account.getCustomer().getId().equals(customerId)) {
            logger.error("Account Mis Match");
            throw new AccountMisMatchException("It's Not Your Account");
        } else {
            accountRepo.delete(account);
            logger.info("Account deleted successfully");
        }
        return "account deleted successfully";
    }

    @Override
    public List<AccountDto> findAllAccounts(Long customerId) {
        logger.info("Fetching All Customers with CUSTOMER-ID");

        customerRepo.findById(customerId).orElseThrow(
                () -> {
                    logger.error("Customer Not Found");
                    throw new CustomerNotFoundException("Customer Not Found");
                }
        );
        List<Account> accounts = accountRepo.findByCustomerId(customerId);
        return accounts.stream()
                .map(account -> mapper.map(account, AccountDto.class))
                .toList();
    }
}