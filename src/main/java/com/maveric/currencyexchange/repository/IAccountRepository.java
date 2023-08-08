package com.maveric.currencyexchange.repository;

import com.maveric.currencyexchange.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccountRepository extends JpaRepository<Account,Long> {
}
