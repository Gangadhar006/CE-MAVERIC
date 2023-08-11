package org.maveric.currencyexchange.entity;

import org.maveric.currencyexchange.enums.GenderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private LocalDate dob;
    @Column(nullable = false, unique = true)
    private String email;
    private Integer age;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    @Column(nullable = false, unique = true)
    private String phone;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Account> accounts;

    @PrePersist
    private void prePersist() {
        LocalDate currentDate = LocalDate.now();
        age = Period.between(dob, currentDate).getYears();
    }
}
