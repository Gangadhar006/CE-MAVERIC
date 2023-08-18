package org.maveric.currencyexchange.payload;

import lombok.Getter;
import lombok.Setter;
import org.maveric.currencyexchange.enums.AccountType;
import org.maveric.currencyexchange.enums.CurrencyType;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountResponse {
    private BigDecimal amount;
    private AccountType accountType;
    private boolean active;
    private CurrencyType currency;
}
