package com.maveric.currencyexchange.entity;

import com.maveric.currencyexchange.enums.GenderType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

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
    @Past
    @Column(nullable = false)
    private LocalDate dob;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    private Integer age;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    @Column(nullable = false, unique = true)
    @Size(min = 10, max = 10)
    private String phone;
    @CreationTimestamp
    private Date createdAt;


    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    @PrePersist
    private void prePersist() {
        LocalDate currentDate = LocalDate.now();
        age = Period.between(dob, currentDate).getYears();
    }
}
