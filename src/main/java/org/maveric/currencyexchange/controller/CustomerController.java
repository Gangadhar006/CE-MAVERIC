package org.maveric.currencyexchange.controller;

import org.maveric.currencyexchange.payload.CustomerRequest;
import org.maveric.currencyexchange.payload.CustomerResponse;
import org.maveric.currencyexchange.service.ICustomerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        logger.info("Requesting customer creation");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createCustomer(customerRequest));
    }

    @PutMapping(value = "/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable long customerId,
            @Valid @RequestBody CustomerRequest customerRequest) {
        logger.info("Requesting customer updation for CUSTOMER");
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.updateCustomer(customerId, customerRequest));

    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAllCustomers() {
        logger.info("Requesting for all customers fetching");
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.findAllCustomers());
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable long customerId) {
        logger.info("Requesting customer deletion for CUSTOMER");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(customerService.deleteCustomer(customerId));
    }
}