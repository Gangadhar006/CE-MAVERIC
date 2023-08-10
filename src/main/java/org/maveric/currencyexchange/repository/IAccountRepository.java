package org.maveric.currencyexchange.repository;

import org.maveric.currencyexchange.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByCustomerId(Long id);
}
