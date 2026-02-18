package com.jackalcode.BootForge.dto;

import com.jackalcode.BootForge.domain.enums.HealthShowDetails;

public record ActuatorConfigRequest(
        String exposedEndpoints,
        HealthShowDetails showHealthDetails
) {
}
