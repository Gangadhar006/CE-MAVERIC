package org.maveric.currencyexchange.service;

import org.maveric.currencyexchange.payload.OrderRequest;
import org.maveric.currencyexchange.payload.OrderResponse;

import java.util.List;

public interface IOrderService {
    List<OrderResponse> fetchAllTransactions(long customerId);

    OrderResponse createTransaction(long customerId, OrderRequest orderRequest);
}
