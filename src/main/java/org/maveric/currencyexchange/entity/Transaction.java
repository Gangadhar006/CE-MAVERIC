package org.maveric.currencyexchange.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq_generator")
    @SequenceGenerator(name = "transaction_seq_generator", sequenceName = "transaction_seq", allocationSize = 1)
    private Long id;
    private String srcAccount;
    private String destAccount;
    private Double amount;
    private String totalValue;
    @CreationTimestamp
    private Date time;
    private Double rate;
    private String currencyPair;
    @ManyToOne
    private Customer customer;

    @PrePersist
    public void calculateValue() {
        totalValue = String.format("%.3f", rate * amount)
                .concat(" ")
                .concat(currencyPair.substring(4, 7));
    }
}
