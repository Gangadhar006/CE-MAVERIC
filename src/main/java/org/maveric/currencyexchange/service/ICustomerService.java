package org.maveric.currencyexchange.service;

import org.maveric.currencyexchange.dtos.CustomerDto;

import java.util.List;

public interface ICustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);

    CustomerDto updateCustomer(Long customerId, CustomerDto customerDto);

    List<CustomerDto> findAllCustomers();

    String deleteCustomer(Long id);
}
