package org.maveric.currencyexchange.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.maveric.currencyexchange.enums.AccountType;
import org.maveric.currencyexchange.enums.CurrencyType;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal amount;
    @Column(nullable = false)
    private Boolean active;
    @CreationTimestamp
    private Date openingDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @PrePersist
    public void prePersist() {
        active = true;
    }

}