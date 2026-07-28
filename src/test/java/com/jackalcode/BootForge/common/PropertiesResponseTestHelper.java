package com.jackalcode.BootForge.common;

import com.jackalcode.BootForge.domain.enums.DatabaseType;
import com.jackalcode.BootForge.dto.*;

public class PropertiesResponseTestHelper {

    public static String expectedProperties(GenerateConfigRequest request) {

        StringBuilder properties = new StringBuilder();

        appendApplicationProperties(properties, request.applicationConfigRequest());
        appendServerProperties(properties, request.serverConfigRequest());
        appendDatabaseProperties(properties, request.databaseConfigRequest());
        appendJpaProperties(properties, request.jpaConfigRequest(), generateSqlDialect(request.databaseConfigRequest().databaseType()));
        appendHikariProperties(properties, request.hikariConfigRequest());
        appendLoggingProperties(properties, request.loggingConfigRequest());
        appendActuatorProperties(properties, request.actuatorConfigRequest());

        return properties.toString().stripTrailing();
    }

    private static void appendApplicationProperties(StringBuilder properties, ApplicationConfigRequest application) {
        if (application == null) {
            return;
        }

        appendProperty(properties, "spring.application.name", application.applicationName());
    }

    private static void appendServerProperties(StringBuilder properties, ServerConfigRequest server) {
        if (server == null) {
            return;
        }

        appendProperty(properties, "server.port", server.port());
        appendProperty(properties, "server.servlet.context-path", server.contextPath());
    }

    private static void appendDatabaseProperties(StringBuilder properties, DatabaseConfigRequest database) {
        if (database == null) {
            return;
        }

        appendProperty(properties, "spring.datasource.url", generateDatasourceUrl(database));
        appendProperty(properties, "spring.datasource.username", database.username());
        appendProperty(properties, "spring.datasource.password", database.password());
        appendProperty(properties, "spring.datasource.driver-class-name", generateDriverClassName(database));
    }

    private static void appendJpaProperties(StringBuilder properties, JpaConfigRequest jpa, String sqlDialect) {
        if (jpa == null) {
            return;
        }

        appendProperty(properties, "spring.jpa.hibernate.ddl-auto", jpa.ddlAuto());
        appendProperty(properties, "spring.jpa.show-sql", jpa.showSql());
        appendProperty(properties, "spring.jpa.open-in-view", jpa.openInView());
        appendProperty(properties, "spring.jpa.database-platform", sqlDialect);
    }

    private static void appendHikariProperties(StringBuilder properties, HikariConfigRequest hikari) {
        if (hikari == null) {
            return;
        }

        appendProperty(properties, "spring.datasource.hikari.minimum-idle", hikari.minimumIdle());
        appendProperty(properties, "spring.datasource.hikari.maximum-pool-size", hikari.maximumPoolSize());
        appendProperty(properties, "spring.datasource.hikari.connection-timeout", hikari.connectionTimeout());
    }

    private static void appendLoggingProperties(StringBuilder properties, LoggingConfigRequest logging) {
        if (logging == null) {
            return;
        }

        appendProperty(properties, "logging.level.root", logging.rootLevel());
        appendProperty(properties, "logging.level.org.springframework.boot", logging.springLevel());
    }

    private static void appendActuatorProperties(StringBuilder properties, ActuatorConfigRequest actuator) {
        if (actuator == null) {
            return;
        }

        appendProperty(properties, "management.endpoints.web.exposure.include", actuator.exposedEndpoints());
        appendProperty(properties, "management.endpoint.health.show-details", actuator.showHealthDetails());
    }

    private static void appendProperty(StringBuilder properties, String key, Object value) {
        if (value == null) {
            return;
        }

        properties.append(key).append("=").append(value).append(System.lineSeparator());
    }

    private static String generateDatasourceUrl(DatabaseConfigRequest database) {
        if (database.databaseType() == null
                || database.host() == null
                || database.databaseName() == null) {
            return null;
        }

        return switch (database.databaseType()) {
            case POSTGRESQL -> "jdbc:postgresql://%s:%d/%s".formatted(
                    database.host(),
                    database.port(),
                    database.databaseName()
            );

            case MYSQL -> "jdbc:mysql://%s:%d/%s".formatted(
                    database.host(),
                    database.port(),
                    database.databaseName()
            );
        };
    }

    private static String generateDriverClassName(DatabaseConfigRequest database) {
        if (database.databaseType() == null) {
            return null;
        }

        return switch (database.databaseType()) {
            case POSTGRESQL -> "org.postgresql.Driver";
            case MYSQL -> "com.mysql.cj.jdbc.Driver";
        };
    }

    private static String generateSqlDialect(DatabaseType databaseType) {
        if (databaseType == null) {
            return null;
        }

        return switch (databaseType) {
            case POSTGRESQL -> "org.hibernate.dialect.PostgreSQLDialect";
            case MYSQL -> "org.hibernate.dialect.MySQLDialect";
        };
    }
}
