package org.maveric.currencyexchange.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.maveric.currencyexchange.enums.GenderType;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class CustomerDto {
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Past
    private LocalDate dob;
    @Email
    private String email;
    private Integer age;
    private GenderType gender;
    @Size(min = 10, max = 10, message = "phone number length should be 10")
    private String phone;
    private Date createdAt;
    private Date updatedAt;
    private List<AccountDto> accounts;
}
