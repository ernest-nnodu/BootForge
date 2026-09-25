package com.jackalcode.BootForge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ApplicationConfigRequest(

        @Schema(
                description = "Name of the Spring Boot application",
                example = "inventory-service"
        )
        @NotBlank(message = "Application name is required")
        String applicationName,

        @Schema(
                description = "Spring profile to activate in the generated configuration",
                example = "prod"
        )
        String activeProfile
) {
}
