package org.maveric.currencyexchange.repository;

import org.maveric.currencyexchange.entity.Transaction;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByCustomerId(Long customerId, Pageable pageable);
}