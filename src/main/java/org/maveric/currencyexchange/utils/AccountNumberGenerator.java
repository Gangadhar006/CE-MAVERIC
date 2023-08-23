package org.maveric.currencyexchange.utils;

import org.maveric.currencyexchange.entity.Account;
import org.maveric.currencyexchange.exception.AccountNotFoundException;
import org.maveric.currencyexchange.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountNumberGenerator {

    private static final String PREFIX = "ACCN-";
    private static final int MIN_RANDOM_NUMBER = 100000;
    private static final int MAX_RANDOM_NUMBER = 900000;

    private final IAccountRepository accountRepository;

    @Autowired
    public AccountNumberGenerator(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String generateUniqueAccountNumber(long id) {
        Random random = new Random();
        String accountNumber;

        do {
            int randomNumber = random.nextInt(MAX_RANDOM_NUMBER) + MIN_RANDOM_NUMBER;
            Account account = accountRepository.findById(id).orElseThrow(
                    () -> {
                        throw new AccountNotFoundException("Account not found");
                    }
            );
            accountNumber = PREFIX + account.getCurrency().name().concat("-").concat(String.valueOf(randomNumber));
        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }
}