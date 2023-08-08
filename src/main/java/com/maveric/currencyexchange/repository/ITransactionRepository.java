package com.maveric.currencyexchange.repository;

import com.maveric.currencyexchange.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
}
