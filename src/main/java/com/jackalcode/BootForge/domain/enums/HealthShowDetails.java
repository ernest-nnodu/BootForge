package com.jackalcode.BootForge.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Supported visibility options for Spring Boot Actuator health details")
public enum HealthShowDetails {

    NEVER,
    WHEN_AUTHORIZED,
    ALWAYS
}
