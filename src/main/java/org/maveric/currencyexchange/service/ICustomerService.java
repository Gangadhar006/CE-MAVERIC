package org.maveric.currencyexchange.service;

import org.maveric.currencyexchange.payload.CustomerRequest;
import org.maveric.currencyexchange.payload.CustomerResponse;

import java.util.List;

public interface ICustomerService {
    CustomerResponse createCustomer(CustomerRequest customerRequest);

    CustomerResponse updateCustomer(long customerId, CustomerRequest customerRequest);

    List<CustomerResponse> findAllCustomers();

    String deleteCustomer(long id);
}
