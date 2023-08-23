package org.maveric.currencyexchange.repository;

import org.maveric.currencyexchange.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByCustomerId(Long customerId);
    boolean existsByAccountNumber(String accountNumber);

    Optional<Account> findByAccountNumber(String accountId);
}