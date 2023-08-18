package org.maveric.currencyexchange.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long srcAccount;
    private Long destAccount;
    private Double amount;
    @CreationTimestamp
    private Date time;
    private Double rate;
    private String currencyPair;

    @ManyToOne
    private Customer customer;
}
