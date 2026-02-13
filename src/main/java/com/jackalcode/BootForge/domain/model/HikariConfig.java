package com.jackalcode.BootForge.domain.model;

public record HikariConfig(
        Integer maximumPoolSize,
        Integer minimumIdle,
        Long connectionTimeout
) {
    public HikariConfig(Integer maximumPoolSize, Integer minimumIdle, Long connectionTimeout) {
        this.maximumPoolSize = (maximumPoolSize == null) ? 10 : maximumPoolSize;
        this.minimumIdle = (minimumIdle == null) ? 2 : minimumIdle;
        this.connectionTimeout = (connectionTimeout == null) ? 30_000L : connectionTimeout;
    }
}
