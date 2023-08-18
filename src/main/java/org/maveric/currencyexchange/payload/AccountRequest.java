package org.maveric.currencyexchange.payload;

import lombok.Getter;
import lombok.Setter;
import org.maveric.currencyexchange.entity.Customer;
import org.maveric.currencyexchange.enums.AccountType;
import org.maveric.currencyexchange.enums.CurrencyType;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountRequest {
    private BigDecimal amount;
    private AccountType accountType;
    private Customer customer;
    private CurrencyType currency;
}