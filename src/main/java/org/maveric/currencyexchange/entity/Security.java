package org.maveric.currencyexchange.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "security_seq_generator")
    @SequenceGenerator(name = "security_seq_generator", sequenceName = "security_seq", allocationSize = 1)
    private long id;
    private String email;
    private String password;

    @OneToOne
    private Customer customer;

    @ManyToOne
    private Roles role;

}
