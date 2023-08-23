package org.maveric.currencyexchange.payload;

import jakarta.validation.constraints.*;
import lombok.*;
import org.maveric.currencyexchange.enums.GenderType;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Only alphabetic characters are allowed")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Only alphabetic characters are allowed")
    private String lastName;
    @NotNull
    @Past(message = "Enter valid Date of Birth")
    private LocalDate dob;
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Enter valid Email")
    private String email;
    @NotNull(message = "Gender is required")
    private GenderType gender;

    @Size(min = 10, max = 10, message = "phone number length should be 10")
    @Pattern(regexp = "\\d+", message = "Phone number should contain only digits")
    private String phone;
}
