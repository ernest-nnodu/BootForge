package com.jackalcode.BootForge.domain.model;

import java.util.Objects;

public record Configuration(
        ApplicationConfig applicationConfig,
        ServerConfig serverConfig,
        DatabaseConfig databaseConfig,
        JpaConfig jpaConfig,
        HikariConfig hikariConfig,
        LoggingConfig loggingConfig,
        ActuatorConfig actuatorConfig
) {
    public Configuration(
            ApplicationConfig applicationConfig,
            ServerConfig serverConfig,
            DatabaseConfig databaseConfig,
            JpaConfig jpaConfig,
            HikariConfig hikariConfig,
            LoggingConfig loggingConfig,
            ActuatorConfig actuatorConfig
    ) {
        this.applicationConfig = Objects.requireNonNull(applicationConfig, "Application config is required");
        this.serverConfig = Objects.requireNonNull(serverConfig, "Server config is required");
        this.databaseConfig = Objects.requireNonNull(databaseConfig, "Database config is required");
        this.jpaConfig = Objects.requireNonNull(jpaConfig, "Jpa config is required");
        this.hikariConfig = Objects.requireNonNull(hikariConfig, "Hikari config is required");
        this.loggingConfig = Objects.requireNonNull(loggingConfig, "Logging config is required");
        this.actuatorConfig = Objects.requireNonNull(actuatorConfig, "Actuator config is required");
    }
}
