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

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }

        int resolvedPort = port == null ? resolveDefaultPort(databaseType) : port;

        if (resolvedPort < 1 || resolvedPort > 65535) {
            throw new IllegalArgumentException("Server port must be between 1 and 65535");
        }

        var resolvedHost = (host == null || host.isBlank()) ? "localhost" : host;

        var resolvedDatabaseName = (databaseName == null || databaseName.isBlank()) ? "app_db" : databaseName;

        this.databaseType = databaseType;
        this.username = username;
        this.password = password;
        this.host = resolvedHost;
        this.databaseName = resolvedDatabaseName;
        this.port = resolvedPort;
    }

    private Integer resolveDefaultPort(DatabaseType databaseType) {

        return switch (databaseType) {
            case DatabaseType.POSTGRESQL -> 5432;
            case DatabaseType.MYSQL -> 3306;
        };
    }

    public String getUrl() {

        return String.format(
                "jdbc:%s://%s:%d/%s",
                databaseType.toString().toLowerCase(),
                host,
                port,
                databaseName
        );
    }
}
