package com.maveric.currencyexchange.service;

import com.maveric.currencyexchange.dtos.CustomerDto;

import java.util.List;

public interface ICustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);

    CustomerDto updateCustomer(Long customerId, CustomerDto customerDto);

    List<CustomerDto> findAllCustomers();

    CustomerDto deleteCustomer(Long id);
}
