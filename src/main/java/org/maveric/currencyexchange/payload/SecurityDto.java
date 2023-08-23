package org.maveric.currencyexchange.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecurityDto {
    private String email;
    private String password;
}