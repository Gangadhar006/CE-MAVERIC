package org.maveric.currencyexchange.payload;

import lombok.Getter;
import lombok.Setter;
import org.maveric.currencyexchange.enums.GenderType;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerResponse {
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private int age;
    private String email;
    private GenderType gender;
    private String phone;
}
