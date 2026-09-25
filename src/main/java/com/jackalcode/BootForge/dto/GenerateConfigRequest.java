package com.jackalcode.BootForge.dto;

import com.jackalcode.BootForge.domain.enums.OutputFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record GenerateConfigRequest(

        @Schema(description = "Application-level configuration settings")
        @Valid
        @NotNull(message = "Application configuration is required")
        ApplicationConfigRequest applicationConfigRequest,

        @Schema(description = "Embedded server configuration settings")
        @Valid
        @NotNull(message = "Server configuration is required")
        ServerConfigRequest serverConfigRequest,

        @Schema(description = "Database connection configuration settings")
        @Valid
        @NotNull(message = "Database configuration is required")
        DatabaseConfigRequest databaseConfigRequest,

        @Schema(description = "JPA and Hibernate configuration settings")
        @Valid
        JpaConfigRequest jpaConfigRequest,

        @Schema(description = "HikariCP database connection pool settings")
        @Valid
        HikariConfigRequest hikariConfigRequest,

        @Schema(description = "Application logging configuration settings")
        @Valid
        LoggingConfigRequest loggingConfigRequest,

        @Schema(description = "Spring Boot Actuator management and monitoring settings")
        @Valid
        ActuatorConfigRequest actuatorConfigRequest,

        @Schema(
                description = "Output format for the generated Spring Boot configuration",
                example = "YAML"
        )
        @NotNull(message = "Output format is required")
        OutputFormat outputFormat
) {
}
