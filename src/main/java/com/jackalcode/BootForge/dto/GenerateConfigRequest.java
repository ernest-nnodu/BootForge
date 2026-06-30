package com.jackalcode.BootForge.dto;

import com.jackalcode.BootForge.domain.enums.OutputFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record GenerateConfigRequest(

        @Valid
        @NotNull(message = "Application configuration is required")
        ApplicationConfigRequest applicationConfigRequest,

        @Valid
        @NotNull(message = "Server configuration is required")
        ServerConfigRequest serverConfigRequest,

        @Valid
        @NotNull(message = "Database configuration is required")
        DatabaseConfigRequest databaseConfigRequest,

        @Valid
        JpaConfigRequest jpaConfigRequest,

        @Valid
        HikariConfigRequest hikariConfigRequest,

        @Valid
        LoggingConfigRequest loggingConfigRequest,

        @Valid
        ActuatorConfigRequest actuatorConfigRequest,

        @NotNull(message = "Output format is required")
        OutputFormat outputFormat
) {
}
