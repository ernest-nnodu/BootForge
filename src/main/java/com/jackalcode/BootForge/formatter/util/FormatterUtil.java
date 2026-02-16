package com.jackalcode.BootForge.formatter.util;

import com.jackalcode.BootForge.domain.enums.DatabaseType;

public class FormatterUtil {

    public static String generateDatasourceUrl(
            DatabaseType databaseType, String databaseName, String host, Integer port) {

        return String.format(
                "jdbc:%s://%s:%d/%s",
                databaseType.toString().toLowerCase(),
                host,
                port,
                databaseName
        );
    }

    public static String generateSQLDialect(DatabaseType databaseType) {

        return switch (databaseType) {
            case DatabaseType.MYSQL -> "MySQLDialect";
            case DatabaseType.POSTGRESQL -> "PostgreSQLDialect";
        };
    }
}
