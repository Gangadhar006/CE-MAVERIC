package org.maveric.currencyexchange.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

import org.maveric.currencyexchange.entity.Account;
import org.maveric.currencyexchange.exception.AccountNotFoundException;
import org.maveric.currencyexchange.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountNumberGenerator {

    private static final String PREFIX = "ACCN-";

    private final IAccountRepository accountRepository;

    @Autowired
    public AccountNumberGenerator(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String generateUniqueAccountNumber(long id) {
        Random random = new Random();
        String accountNumber;

        do {
            int randomNumber = random.nextInt(900000) + 100000;
            Account account = accountRepository.findById(id).orElseThrow(
                    () -> {
                        throw new AccountNotFoundException("Account not found");
                    }
            );
            accountNumber = PREFIX + account.getCurrency().name() + "-" + randomNumber;
        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }
}
