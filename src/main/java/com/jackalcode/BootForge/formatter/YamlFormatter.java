package com.jackalcode.BootForge.formatter;

import com.jackalcode.BootForge.domain.enums.DatabaseType;
import com.jackalcode.BootForge.domain.model.*;
import com.jackalcode.BootForge.formatter.util.FormatterUtil;

public class YamlFormatter implements ConfigFormatter {

    @Override
    public String format(Configuration configuration) {

        return """
                spring:
                # ----------Application Configuration----------#
                  %s
                 \s
                  datasource:
                # ----------Database Configuration----------#
                    %s
                   \s
                # ----------Hikari Configuration----------#
                    %s
                # ----------JPA Configuration----------#
                  %s
                # ----------Server Configuration----------#
                %s
                # ----------Logging Configuration----------#
                %s
                # ----------Actuator Configuration----------#
                %s
               \s"""
                .formatted(
                        formatApplicationConfig(configuration.applicationConfig()),
                        formatDatabaseConfig(configuration.databaseConfig()),
                        formatHikariConfig(configuration.hikariConfig()),
                        formatJpaConfig(configuration.jpaConfig(), configuration.databaseConfig().databaseType()),
                        formatServerConfig(configuration.serverConfig()),
                        formatLoggingConfig(configuration.loggingConfig()),
                        formatActuatorConfig(configuration.actuatorConfig())
                );
    }

    private String formatApplicationConfig(ApplicationConfig applicationConfig) {

        return """
                  application:
                    name:%s
                   \s
                  profiles:
                    active:%s
                   \s
               \s"""
                .formatted(
                        applicationConfig.applicationName(),
                        applicationConfig.activeProfile()
                );
    }

    private String formatDatabaseConfig(DatabaseConfig databaseConfig) {

        return """
                    url:%s
                    username:%s
                    password:%s
                   \s
               \s"""
                .formatted(
                        FormatterUtil.generateDatasourceUrl(
                                databaseConfig.databaseType(),
                                databaseConfig.databaseName(),
                                databaseConfig.host(),
                                databaseConfig.port()
                        ),
                        databaseConfig.username(),
                        databaseConfig.password()
                );
    }

    private String formatHikariConfig(HikariConfig hikariConfig) {

        return """
                  hikari:
                    maximum-pool-size:%d
                    minimum-idle:%d
                    connection-timeout:%d
                   \s
               \s"""
                .formatted(
                        hikariConfig.maximumPoolSize(),
                        hikariConfig.minimumIdle(),
                        hikariConfig.connectionTimeout()
                );
    }

    private String formatJpaConfig(JpaConfig jpaConfig, DatabaseType databaseType) {

        return """
                jpa:
                  hibernate:
                    ddl-auto:%s
                  database:
                    platform:%s
                  show-sql:%s
                  open-in-view:%s
                 \s
               \s"""
                .formatted(
                        jpaConfig.ddlAuto(),
                        FormatterUtil.generateSQLDialect(databaseType),
                        jpaConfig.showSql(),
                        jpaConfig.openInView()
                );
    }

    private String formatServerConfig(ServerConfig serverConfig) {

        return """
                server:
                  port:%d
                  servlet:
                    context-path:%s
                   \s
               \s"""
                .formatted(
                        serverConfig.port(),
                        serverConfig.contextPath()
                );
    }

    private String formatLoggingConfig(LoggingConfig loggingConfig) {

        return """
                logging:
                  level:
                    root:%s
                    org.springframework:%s
                   \s
               \s"""
                .formatted(
                        loggingConfig.rootLevel(),
                        loggingConfig.springLevel()
                );
    }

    private String formatActuatorConfig(ActuatorConfig actuatorConfig) {

        return """
                management:
                  endpoints:
                    web:
                      exposure:
                        include: %s
                  endpoint:
                    health:
                      show-details: %s
                     \s
               \s"""
                .formatted(
                        actuatorConfig.exposedEndpoints(),
                        actuatorConfig.showHealthDetails()
                );
    }
}
