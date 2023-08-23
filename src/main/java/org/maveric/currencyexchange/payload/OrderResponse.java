package org.maveric.currencyexchange.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderResponse {
    private String srcAccount;
    private String destAccount;
    private double amount;
    private Date time;
    private double rate;
    private String currencyPair;
    private String  totalValue;
}