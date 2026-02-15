package com.jackalcode.BootForge.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ServerConfigRequest(
        @Min(1)
        @Max(65535)
        Integer port,

        String contextPath
) {
}
