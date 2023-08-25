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
        return mapper.map(account, AccountResponse.class);
    }


    @Override
    @Transactional
    public AccountResponse updateAccount(long customerId, String accountNumber, AccountRequest accountRequest) {
        verfiyCustomer(customerId);
        return updateAccountUtil(customerId, accountNumber, accountRequest);
    }

    public AccountResponse updateAccountUtil(long customerId, String accountNumber, AccountRequest accountRequest) {
        Account account = verifyAccount(accountNumber, customerId);
//        account.setAmount(accountRequest.getAmount());
//        account.setAccountType(accountRequest.getAccountType());
//        account.setCurrency(accountRequest.getCurrency());
        mapper.map(accountRequest, account);
        account = accountRepo.save(account);

        return mapper.map(account, AccountResponse.class);
    }

    @Override
    @Transactional
    public String deleteAccount(long customerId, String accountNumber) {
        Account account = verifyAccount(accountNumber, customerId);
        accountRepo.delete(account);
        return "account deleted successfully";
    }

    @Override
    public List<AccountResponse> findAllAccounts(long customerId) {
        verfiyCustomer(customerId);
        List<Account> accounts = accountRepo.findByCustomerId(customerId);
        return accounts.stream()
                .map(account -> mapper.map(account, AccountResponse.class))
                .toList();
    }

    @Override
    public AccountResponse findAccount(long customerId, String accountNumber) {
        Account account = verifyAccount(accountNumber, customerId);
        return mapper.map(account, AccountResponse.class);
    }

    public Account verifyAccount(String accountNumber, long customerId) {
        Account account = accountRepo.findByAccountNumber(accountNumber).orElseThrow(AccountNotFoundException::new);

        boolean isAccountOwner = account.getCustomer().getId().equals(customerId);

        if (!isAccountOwner) {
            throw new AccountMisMatchException();
        }
        return account;
    }

    public Customer verfiyCustomer(long customerId) {
        return customerRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    }
}