package com.jackalcode.BootForge.dto;

import com.jackalcode.BootForge.domain.enums.HealthShowDetails;
import io.swagger.v3.oas.annotations.media.Schema;

public record ActuatorConfigRequest(

        @Schema(
                description = "Comma-separated list of Spring Boot Actuator endpoints to expose over HTTP",
                example = "health,info"
        )
        String exposedEndpoints,

        @Schema(
                description = "Controls when detailed health information is included in the Actuator health response",
                example = "ALWAYS"
        )
        HealthShowDetails showHealthDetails
) {
}
