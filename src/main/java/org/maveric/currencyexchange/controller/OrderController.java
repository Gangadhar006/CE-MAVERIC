package org.maveric.currencyexchange.controller;

import jakarta.validation.Valid;
import org.maveric.currencyexchange.payload.OrderRequest;
import org.maveric.currencyexchange.payload.OrderResponse;
import org.maveric.currencyexchange.repository.ITransactionRepository;
import org.maveric.currencyexchange.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customers/{customerId}/orders")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private IOrderService orderService;
    @Autowired
    private ITransactionRepository repo;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> fetchAllTransactions(@PathVariable long customerId) {
        log.info("Requesting for Fetching all Transactions");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.fetchAllTransactions(customerId));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createTransaction(@PathVariable long customerId,
                                                           @Valid @RequestBody OrderRequest orderRequest) {
        log.info("Requesting for Creating Transaction");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.createTransaction(customerId, orderRequest));
    }
}
