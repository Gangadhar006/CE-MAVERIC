package org.maveric.currencyexchange.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderRequest {
    private long srcAccount;
    private long destAccount;
    private double amount;
    private CustomerResponse customer;
}
