package com.jackalcode.BootForge.domain.model;

import com.jackalcode.BootForge.domain.enums.HealthShowDetails;

public record ActuatorConfig(
        String exposedEndpoints,
        HealthShowDetails showHealthDetails
) {
    public ActuatorConfig(String exposedEndpoints, HealthShowDetails showHealthDetails) {
        this.exposedEndpoints = (exposedEndpoints == null || exposedEndpoints.isBlank()) ?
                "health,info" : exposedEndpoints;

        this.showHealthDetails = showHealthDetails != null ? showHealthDetails : HealthShowDetails.NEVER;
    }
}
