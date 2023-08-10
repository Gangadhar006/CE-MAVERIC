package org.maveric.currencyexchange.repository;

import org.maveric.currencyexchange.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
}
