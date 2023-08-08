package com.maveric.currencyexchange.dtos;

import com.maveric.currencyexchange.entity.Account;
import com.maveric.currencyexchange.enums.GenderType;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    @Past
    private LocalDate dob;
    @Email
    private String email;
    private Integer age;
    private GenderType gender;
    @Length(min = 10, max = 10)
    private String phone;

    @PrePersist
    private void prePersist() {
        LocalDate currentDate = LocalDate.now();
        age = Period.between(dob, currentDate).getYears();
    }
}
