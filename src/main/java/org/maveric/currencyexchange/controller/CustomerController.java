package org.maveric.currencyexchange.controller;

import org.maveric.currencyexchange.dtos.CustomerDto;
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
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        logger.info("Requesting customer creation");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createCustomer(customerDto));
    }

    @PatchMapping(value = "/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(
            @PathVariable("customerId") Long customerId,
            @Valid @RequestBody CustomerDto customerDto) {
        logger.info("Requesting customer updation for CUSTOMER");
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.updateCustomer(customerId, customerDto));

    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> findAllCustomers() {
        logger.info("Requesting for all customers fetching");
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.findAllCustomers());
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("customerId") Long customerId) {
        logger.info("Requesting customer deletion for CUSTOMER");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(customerService.deleteCustomer(customerId));
    }
}