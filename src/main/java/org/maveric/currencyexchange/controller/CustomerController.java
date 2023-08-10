package org.maveric.currencyexchange.controller;

import org.maveric.currencyexchange.dtos.CustomerDto;
import org.maveric.currencyexchange.service.ICustomerService;
import jakarta.validation.Valid;
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
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createCustomer(customerDto));
    }

    @PatchMapping(value = "/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(
            @PathVariable("customerId") Long customerId,
            @Valid @RequestBody CustomerDto customerDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.updateCustomer(customerId, customerDto));

    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> findAllCustomers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.findAllCustomers());
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(customerService.deleteCustomer(customerId));
    }
}