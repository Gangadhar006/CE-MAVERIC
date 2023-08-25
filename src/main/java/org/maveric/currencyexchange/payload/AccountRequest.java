package org.maveric.currencyexchange.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maveric.currencyexchange.entity.Customer;
import org.maveric.currencyexchange.enums.AccountType;
import org.maveric.currencyexchange.enums.CurrencyType;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount should be Positive")
    private BigDecimal amount;
    @NotNull(message = "Account type is required")
    private AccountType accountType;
    @NotNull(message = "Currency is required")
    private CurrencyType currency;
    private boolean active;
    private String accountNumber;
}