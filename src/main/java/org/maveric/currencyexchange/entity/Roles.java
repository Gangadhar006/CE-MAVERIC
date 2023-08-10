package org.maveric.currencyexchange.entity;

import org.maveric.currencyexchange.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private RoleType role;
    @Column(unique = true, nullable = false)
    private String description;

    @OneToMany(mappedBy = "role")
    private List<Security> security;
}