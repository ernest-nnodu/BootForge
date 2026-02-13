package com.jackalcode.BootForge.domain.model;

public record ActuatorConfig(
        String exposedEndpoints,
        Boolean showHealthDetails
) {
    public ActuatorConfig(String exposedEndpoints, Boolean showHealthDetails) {
        this.exposedEndpoints = (exposedEndpoints == null || exposedEndpoints.isBlank()) ?
                "health,info" : exposedEndpoints;

        this.showHealthDetails = showHealthDetails != null && showHealthDetails;
    }
}
