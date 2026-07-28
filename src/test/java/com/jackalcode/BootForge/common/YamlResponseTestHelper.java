package com.jackalcode.BootForge.common;

import com.jackalcode.BootForge.domain.enums.DatabaseType;
import com.jackalcode.BootForge.dto.*;

import java.util.Map;

public class YamlResponseTestHelper {

    public static String expectedYaml(GenerateConfigRequest request) {

        StringBuilder yaml = new StringBuilder();

        appendApplication(yaml, request.applicationConfigRequest());
        appendServer(yaml, request.serverConfigRequest());
        appendDatabase(yaml, request.databaseConfigRequest());
        appendJpa(yaml, request.jpaConfigRequest());
        appendLogging(yaml, request.loggingConfigRequest());
        appendActuator(yaml, request.actuatorConfigRequest());

        return yaml.toString();
    }

    public static Map<String, Object> mapAt(Map<String, Object> parent, String key) {
        Object value = parent.get(key);

        return (Map<String, Object>) value;
    }

    private static void appendApplication(
            StringBuilder yaml,
            ApplicationConfigRequest application) {

        yaml.append("""
                spring:
                  application:
                    name: %s
                                
                  profiles:
                    active: %s
                """.formatted(
                application.applicationName(),
                application.activeProfile()));
    }

    private static void appendServer(
            StringBuilder yaml,
            ServerConfigRequest server) {

        yaml.append("""
                                
                server:
                  port: %d
                  servlet:
                    context-path: %s
                """.formatted(
                server.port(),
                server.contextPath()));
    }

    private static void appendDatabase(
            StringBuilder yaml,
            DatabaseConfigRequest database) {

        if (database == null) {
            return;
        }

        yaml.append("""
                                
                spring:
                  datasource:
                    url: %s
                    username: %s
                    password: %s
                """.formatted(
                generateDatasourceUrl(database),
                database.username(),
                database.password()));
    }

    private static void appendJpa(
            StringBuilder yaml,
            JpaConfigRequest jpa) {

        if (jpa.ddlAuto() == null || jpa.showSql() == null || jpa.openInView() == null) {
            return;
        }

        yaml.append("""
                    jpa:
                      show-sql: %s
                      open-in-view: %s
                                
                      hibernate:
                        ddl-auto: %s
                """.formatted(
                jpa.showSql(),
                jpa.openInView(),
                jpa.ddlAuto().name().toLowerCase()));
    }

    private static void appendLogging(
            StringBuilder yaml,
            LoggingConfigRequest logging) {

        if (logging.rootLevel() == null || logging.springLevel() == null) {
            return;
        }

        yaml.append("""
                                
                logging:
                  level:
                    root: %s
                    org.springframework: %s
                """.formatted(
                logging.rootLevel().name(),
                logging.springLevel().name()));
    }

    private static void appendActuator(
            StringBuilder yaml,
            ActuatorConfigRequest actuator) {

        if (actuator.showHealthDetails() == null || actuator.exposedEndpoints() == null) {
            return;
        }

        yaml.append("""
                                
                management:
                  endpoints:
                    web:
                      exposure:
                        include: %s
                                
                  endpoint:
                    health:
                      show-details: %s
                """.formatted(
                String.join(",", actuator.exposedEndpoints()),
                actuator.showHealthDetails().name().toLowerCase()));
    }

    private static String generateDatasourceUrl(
            DatabaseConfigRequest database) {

        int port = database.port() != null
                ? database.port()
                : defaultPort(database.databaseType());

        return switch (database.databaseType()) {
            case POSTGRESQL ->
                    "jdbc:postgresql://%s:%d/%s".formatted(
                            database.host(),
                            port,
                            database.databaseName());

            case MYSQL ->
                    "jdbc:mysql://%s:%d/%s".formatted(
                            database.host(),
                            port,
                            database.databaseName());
        };
    }

    private static int defaultPort(DatabaseType type) {
        return switch (type) {
            case POSTGRESQL -> 5432;
            case MYSQL -> 3306;
        };
    }
}
