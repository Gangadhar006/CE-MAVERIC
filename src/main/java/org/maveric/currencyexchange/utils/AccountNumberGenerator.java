package org.maveric.currencyexchange.utils;

import org.maveric.currencyexchange.entity.Account;
import org.maveric.currencyexchange.exception.AccountNotFoundException;
import org.maveric.currencyexchange.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountNumberGenerator {

    @Value("${account.prefix}")
    private String prefix;
    @Value("${randomMin}")
    private int minRandomNumber;
    @Value("${randomMax}")
    private int maxRandomNumber;

    private final IAccountRepository accountRepository;

    @Autowired
    public AccountNumberGenerator(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String generateUniqueAccountNumber(long id) {
        Random random = new Random();
        String accountNumber;
        do {
            int randomNumber = random.nextInt(maxRandomNumber) + minRandomNumber;
            Account account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
            accountNumber = prefix.concat(account.getCurrency().name().concat(String.valueOf(randomNumber)));
        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }
}