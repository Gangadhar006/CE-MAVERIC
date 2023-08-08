package com.maveric.currencyexchange.dtos;

import com.maveric.currencyexchange.entity.Customer;
import com.maveric.currencyexchange.entity.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SecurityDto {
    private String email;
    private String password;
}