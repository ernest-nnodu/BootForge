package com.jackalcode.BootForge.domain.model;

import com.jackalcode.BootForge.domain.enums.DatabaseType;

public record DatabaseConfig(
        DatabaseType databaseType,
        String username,
        String password,
        String host,
        String databaseName,
        Integer port) {

    public DatabaseConfig(DatabaseType databaseType, String username, String password,
                          String host, String databaseName, Integer port) {

        if (databaseType == null) {
            throw new IllegalArgumentException("Database type is required");
        }

        if (username == null) {
            throw new IllegalArgumentException("Username is required");
        }

        if (password == null) {
            throw new IllegalArgumentException("Password is required");
        }

        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Port must be between 1 and 65535");
        }

        this.databaseType = databaseType;
        this.username = username;
        this.password = password;
        this.host = (host == null || host.isBlank()) ? "localhost" : host;
        this.databaseName = (databaseName == null || databaseName.isBlank()) ? "app_db" : databaseName;
        this.port = port == null ? resolveDefaultPort(databaseType) : port;
    }

    private Integer resolveDefaultPort(DatabaseType databaseType) {

        return switch (databaseType) {
            case DatabaseType.POSTGRESQL -> 5432;
            case DatabaseType.MYSQL -> 3306;
        };
    }
}
