package com.jackalcode.BootForge.dto;

import com.jackalcode.BootForge.domain.enums.DatabaseType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatabaseConfigRequest(
        @NotNull(message = "Database type must not be null")
        DatabaseType databaseType,

        @NotBlank(message = "Username must not be blank")
        String username,

        @NotBlank(message = "Password must not be blank")
        String password,

        String host,
        String databaseName,

        @Min(value = 1, message = "Port must be greater than or equal to 1")
        @Max(value = 65535, message = "Port must be less than or equal to 65535")
        Integer port
) {
}
