package org.maveric.currencyexchange.controller;

import jakarta.validation.Valid;
import org.maveric.currencyexchange.payload.OrderRequest;
import org.maveric.currencyexchange.payload.OrderResponse;
import org.maveric.currencyexchange.service.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
@CrossOrigin(origins = "${corsAllowedOrigin}")
public class OrderController {
    private IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/watchlist/{customerId}")
    public ResponseEntity<List<OrderResponse>> fetchAllTransactions(
            @RequestParam(defaultValue = "${page.default}") Integer page,
            @RequestParam(defaultValue = "${size.default}") Integer size,
            @PathVariable long customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.fetchAllTransactions(page, size, customerId));
    }

    @PostMapping(value = "/placeorder/{customerId}")
    public ResponseEntity<OrderResponse> createTransaction(@PathVariable long customerId,
                                                           @Valid @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.createTransaction(customerId, orderRequest));
    }
}