package com.maveric.currencyexchange.controller;

import com.maveric.currencyexchange.dtos.CustomerDto;
import com.maveric.currencyexchange.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    private ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createCustomer(customerDto));
    }

    @PatchMapping(value = "/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(
            @PathVariable("customerId") Long customerId,
            @RequestBody CustomerDto customerDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.updateCustomer(customerId, customerDto));

    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> findAllCustomers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.findAllCustomers());
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<CustomerDto> deleteCustomer(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(customerService.deleteCustomer(customerId));
    }
}