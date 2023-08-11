package org.maveric.currencyexchange.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.maveric.currencyexchange.enums.AccountType;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class AccountDto {
    private Long id;
    @NotBlank
    private BigDecimal amount;
    private Boolean active;
    @NotBlank
    private AccountType accountType;
    @NotBlank
    private String currency;

    private Date openingDate;
//    @OneToMany(mappedBy = "account")
//    private List<Transaction> transactions;

}