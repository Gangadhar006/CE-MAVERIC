package org.maveric.currencyexchange.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
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
    private Customer customer;
    @NotNull(message = "Currency is required")
    private CurrencyType currency;
}