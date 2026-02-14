package com.jackalcode.BootForge.dto;

import jakarta.validation.constraints.NotBlank;

public record ApplicationConfigRequest(
        @NotBlank
        String applicationName,

        String activeProfile
) {
}
