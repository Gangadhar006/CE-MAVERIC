package org.maveric.currencyexchange.serviceimpl;

import org.maveric.currencyexchange.dtos.AccountDto;
import org.maveric.currencyexchange.entity.Account;
import org.maveric.currencyexchange.entity.Customer;
import org.maveric.currencyexchange.repository.IAccountRepository;
import org.maveric.currencyexchange.repository.ICustomerRepository;
import org.maveric.currencyexchange.service.IAccountService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

        //throw CustomerNotFoundException

        Optional<Customer> customer = customerRepo.findById(customerId);
        if (!Objects.isNull(accountDto)) {
            Account account = mapper.map(accountDto, Account.class);
            account.setCustomer(customer.get());
            account = accountRepo.save(account);
            accountDto = mapper.map(account, AccountDto.class);
        }
        return accountDto;
    }

    @Override
    @Transactional
    public AccountDto updateAccount(Long customerId, Long accountId, AccountDto accountDto) {

        //throw CustomerNotFoundException

        Optional<Customer> customer = customerRepo.findById(customerId);

        //throw AccountNotFoundException

        Optional<Account> account = accountRepo.findById(accountId);
        accountDto = updateAccountUtil(customerId, accountId, accountDto);

        return accountDto;
    }

    public AccountDto updateAccountUtil(Long customerId, Long accountId, AccountDto accountDto) {


        if (!Objects.isNull(accountDto)) {

            //throw AccountNotFoundException

            Optional<Account> account = accountRepo.findById(accountId);
            Account accountCopy = account.get();
            boolean isAccountOwner = accountCopy.getCustomer().getId().equals(customerId);

            // throw AccountMisMatchException

            if (isAccountOwner) {
                if (accountDto.getAmount() != null)
                    accountCopy.setAmount(accountDto.getAmount());

                if (accountDto.getActive() != null)
                    accountCopy.setActive(accountDto.getActive());

                if (accountDto.getAccountType() != null)
                    accountCopy.setAccountType(accountDto.getAccountType());

                if (accountDto.getCurrency() != null)
                    accountCopy.setCurrency(accountDto.getCurrency());
            }

            accountCopy = accountRepo.save(accountCopy);
            accountDto = mapper.map(accountCopy, AccountDto.class);
        }
        return accountDto;
    }


    @Override
    @Transactional
    public AccountDto deleteAccount(Long customerId, Long accountId) {

        //throw CustomerNotFoundException

        Optional<Customer> customer = customerRepo.findById(customerId);
        Optional<Account> account = accountRepo.findById(accountId);
        boolean isAccountOwner = account.get().getCustomer().getId().equals(customerId);
        if (isAccountOwner)
            accountRepo.delete(account.get());
        return null;
    }

    @Override
    public List<AccountDto> findAllAccounts(Long customerId) {
        List<Account> accounts = accountRepo.findByCustomerId(customerId);
        return accounts.stream()
                .map(account -> mapper.map(account, AccountDto.class))
                .toList();
    }
}