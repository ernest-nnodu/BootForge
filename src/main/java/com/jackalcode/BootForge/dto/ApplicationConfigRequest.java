package com.jackalcode.BootForge.dto;

import jakarta.validation.constraints.NotBlank;

public record ApplicationConfigRequest(
        @NotBlank(message = "Application name is required")
        String applicationName,

        String activeProfile
) {
}
