package org.maveric.currencyexchange.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderRequest {
    @Positive(message = "Source account can't be Blank")
    private long srcAccount;
    @Positive(message = "Destination account can't be Blank")
    private long destAccount;
    @Positive(message = "Amount must be a positive number and Not Blank")
    private double amount;
    private CustomerResponse customer;
}
