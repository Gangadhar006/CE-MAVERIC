package com.maveric.currencyexchange.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = {
                @Server(
                        description = "LOCAL ENV",
                        url = "http://localhost:8081"
                ),
        }

)
public class SwaggerConfig {
}