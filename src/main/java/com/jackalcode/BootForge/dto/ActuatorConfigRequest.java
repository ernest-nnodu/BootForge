package com.jackalcode.BootForge.dto;

public record ActuatorConfigRequest(
        String exposedEndpoints,
        Boolean showHealthDetails
) {
}
