package com.jackalcode.BootForge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ServerConfigRequest(
        @Schema(
                description = "Port on which the application server listens for incoming requests",
                example = "8080"
        )
        @Min(value = 1, message = "Port number must be greater than 0")
        @Max(value = 65535, message = "Port number must be less than or equal to 65535")
        Integer port,

        @Schema(
                description = "Base context path under which the application endpoints are served",
                example = "/api"
        )
        String contextPath
) {
}
