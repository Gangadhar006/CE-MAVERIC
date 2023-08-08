package com.maveric.currencyexchange.dtos;

import com.maveric.currencyexchange.entity.Customer;
import com.maveric.currencyexchange.entity.Transaction;
import com.maveric.currencyexchange.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AccountDto {
    private String accountNo;
    private Float amount;
    private Boolean active;
    private Date openingDate;
    private AccountType accountType;

    private Customer customer;
    private List<Transaction> transactions;

}
