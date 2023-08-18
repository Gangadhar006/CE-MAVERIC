package org.maveric.currencyexchange.payload;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.maveric.currencyexchange.enums.GenderType;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerRequest {
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String email;
    private GenderType gender;

    @Size(min = 10, max = 10, message = "phone number length should be 10")
    private String phone;
}
