package com.jackalcode.BootForge.mapper;

import com.jackalcode.BootForge.domain.model.*;
import com.jackalcode.BootForge.dto.GenerateConfigRequest;

public class ConfigurationMapper {

    public Configuration toDomain(GenerateConfigRequest configRequest) {

        ApplicationConfig applicationConfig = new ApplicationConfig(
                configRequest.applicationConfigRequest().applicationName(),
                configRequest.applicationConfigRequest().activeProfile()
        );

        ServerConfig serverConfig = new ServerConfig(
                configRequest.serverConfigRequest().port(),
                configRequest.serverConfigRequest().contextPath()
        );

        DatabaseConfig databaseConfig = new DatabaseConfig(
                configRequest.databaseConfigRequest().databaseType(),
                configRequest.databaseConfigRequest().username(),
                configRequest.databaseConfigRequest().password(),
                configRequest.databaseConfigRequest().host(),
                configRequest.databaseConfigRequest().databaseName(),
                configRequest.databaseConfigRequest().port()
        );

        JpaConfig jpaConfig = new JpaConfig(
                configRequest.jpaConfigRequest().ddlAuto(),
                configRequest.jpaConfigRequest().showSql(),
                configRequest.jpaConfigRequest().openInView()
        );

        HikariConfig hikariConfig = new HikariConfig(
                configRequest.hikariConfigRequest().maximumPoolSize(),
                configRequest.hikariConfigRequest().minimumIdle(),
                configRequest.hikariConfigRequest().connectionTimeout()
        );

        LoggingConfig loggingConfig = new LoggingConfig(
                configRequest.loggingConfigRequest().rootLevel(),
                configRequest.loggingConfigRequest().springLevel()
        );

        ActuatorConfig actuatorConfig = new ActuatorConfig(
                configRequest.actuatorConfigRequest().exposedEndpoints(),
                configRequest.actuatorConfigRequest().showHealthDetails()
        );

        return new Configuration(
                applicationConfig,
                serverConfig,
                databaseConfig,
                jpaConfig,
                hikariConfig,
                loggingConfig,
                actuatorConfig
        );
    }
}
