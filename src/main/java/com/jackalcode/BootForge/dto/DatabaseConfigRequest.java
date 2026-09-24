package com.jackalcode.BootForge.dto;

import com.jackalcode.BootForge.domain.enums.DatabaseType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatabaseConfigRequest(

        @Schema(
                description = "Database type used to generate the datasource configuration",
                example = "POSTGRESQL"
        )
        @NotNull(message = "Database type must not be null")
        DatabaseType databaseType,

        @Schema(
                description = "Username used to connect to the database",
                example = "admin"
        )
        @NotBlank(message = "Username must not be blank")
        String username,

        @Schema(
                description = "Password used to connect to the database",
                example = "secret"
        )
        @NotBlank(message = "Password must not be blank")
        String password,

        @Schema(
                description = "Hostname or IP address of the database server",
                example = "localhost"
        )
        String host,

        @Schema(
                description = "Name of the database to connect to",
                example = "inventory_db"
        )
        String databaseName,

        @Schema(
                description = "Port on which the database server accepts connections",
                example = "5432"
        )
        @Min(value = 1, message = "Port must be greater than or equal to 1")
        @Max(value = 65535, message = "Port must be less than or equal to 65535")
        Integer port
) {
}
