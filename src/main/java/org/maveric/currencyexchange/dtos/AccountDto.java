package org.maveric.currencyexchange.dtos;

import org.maveric.currencyexchange.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class AccountDto {
    private Long id;
    private BigDecimal amount;
    private Boolean active;
    private AccountType accountType;
    private String currency;

//    @OneToMany(mappedBy = "account")
//    private List<Transaction> transactions;

}