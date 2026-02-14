package com.jackalcode.BootForge.dto;

public record HikariConfigRequest(
        Integer maximumPoolSize,
        Integer minimumIdle,
        Long connectionTimeout
) {
}
