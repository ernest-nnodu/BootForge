package com.jackalcode.BootForge.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ServerConfigRequest(
        @Min(value = 1, message = "Port number must be greater than 0")
        @Max(value = 65535, message = "Port number must be less than or equal to 65535")
        Integer port,

        String contextPath
) {
}
