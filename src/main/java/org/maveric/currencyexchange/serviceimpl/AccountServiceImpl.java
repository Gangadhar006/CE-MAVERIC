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
        Customer customer = customerRepo.findById(customerId).orElseThrow(
                () -> {
                    logger.error("Customer Not Found");
                    throw new CustomerNotFoundException("Customer Not Found");
                }
        );

        Account account = mapper.map(accountDto, Account.class);
        account.setCustomer(customer);
        account = accountRepo.save(account);
        accountDto = mapper.map(account, AccountDto.class);
        logger.info("Account created successfully for CUSTOMER");
        return accountDto;
    }


    @Override
    @Transactional
    public AccountDto updateAccount(Long customerId, Long accountId, AccountDto accountDto) {
        logger.info("Updating account with CUSTOMER");

        customerRepo.findById(customerId).orElseThrow(
                () -> {
                    logger.error("Customer Not Found");
                    throw new CustomerNotFoundException("Customer Not Found");
                }
        );

        accountDto = updateAccountUtil(customerId, accountId, accountDto);
        return accountDto;
    }

    public AccountDto updateAccountUtil(Long customerId, Long accountId, AccountDto accountDto) {
        Account account = accountRepo.findById(accountId).orElseThrow(
                () -> {
                    logger.error("Account Not Found");
                    throw new AccountNotFoundException("Account Not Found");
                }
        );

        boolean isAccountOwner = account.getCustomer().getId().equals(customerId);

        if (isAccountOwner) {
            account.setAmount(accountDto.getAmount());
            account.setActive(accountDto.getActive());
            account.setAccountType(accountDto.getAccountType());
            account.setCurrency(accountDto.getCurrency());

            account = accountRepo.save(account);
            accountDto = mapper.map(account, AccountDto.class);

            return accountDto;
        } else {
            logger.error("Account Mis Match CUSTOMER");
            throw new AccountMisMatchException("It's Not Your Account");
        }
    }


    @Override
    @Transactional
    public String deleteAccount(Long customerId, Long accountId) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> {
                    logger.error("Account Not Found");
                    return new AccountNotFoundException("Account Not Found");
                });

        boolean isAccountOwner = account.getCustomer().getId().equals(customerId);

        if (isAccountOwner) {
            accountRepo.delete(account);
            logger.info("Account deleted successfully");
            return "account deleted successfully";
        } else {
            logger.error("Account Mis Match");
            throw new AccountMisMatchException("It's Not Your Account");
        }
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