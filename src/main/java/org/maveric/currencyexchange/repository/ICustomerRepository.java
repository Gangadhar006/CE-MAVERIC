package org.maveric.currencyexchange.repository;

import org.maveric.currencyexchange.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer,Long> {
}
