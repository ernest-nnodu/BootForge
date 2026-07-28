package com.jackalcode.BootForge.common;

import com.jackalcode.BootForge.domain.model.*;
import com.jackalcode.BootForge.dto.*;

public class ConfigurationTestHelper {

    public static Configuration toConfiguration(GenerateConfigRequest request) {

        return new Configuration(
                toApplicationConfiguration(request.applicationConfigRequest()),
                toServerConfiguration(request.serverConfigRequest()),
                toDatabaseConfiguration(request.databaseConfigRequest()),
                toJpaConfiguration(request.jpaConfigRequest()),
                toHikariCOnfiguration(request.hikariConfigRequest()),
                toLoggingConfiguration(request.loggingConfigRequest()),
                toActuatorConfiguration(request.actuatorConfigRequest())
        );
    }

    private static ActuatorConfig toActuatorConfiguration(ActuatorConfigRequest actuatorConfigRequest) {

        if (actuatorConfigRequest == null) {
            return new ActuatorConfig(null, null);
        }

        return new ActuatorConfig(
                actuatorConfigRequest.exposedEndpoints(),
                actuatorConfigRequest.showHealthDetails()
        );
    }

    private static LoggingConfig toLoggingConfiguration(LoggingConfigRequest loggingConfigRequest) {

        if (loggingConfigRequest == null) {
            return new LoggingConfig(null, null);
        }

        return new LoggingConfig(
                loggingConfigRequest.rootLevel(),
                loggingConfigRequest.springLevel()
        );
    }

    private static HikariConfig toHikariCOnfiguration(HikariConfigRequest hikariConfigRequest) {

        if (hikariConfigRequest == null) {
            return new HikariConfig(null, null, null);
        }

        return new HikariConfig(
                hikariConfigRequest.maximumPoolSize(),
                hikariConfigRequest.minimumIdle(),
                hikariConfigRequest.connectionTimeout()
        );
    }

    private static JpaConfig toJpaConfiguration(JpaConfigRequest jpaConfigRequest) {

        if (jpaConfigRequest == null) {
            return new JpaConfig(null, null, null);
        }

        return new JpaConfig(
                jpaConfigRequest.ddlAuto(),
                jpaConfigRequest.showSql(),
                jpaConfigRequest.openInView()
        );
    }

    private static DatabaseConfig toDatabaseConfiguration(DatabaseConfigRequest databaseConfigRequest) {

        return new DatabaseConfig(
                databaseConfigRequest.databaseType(),
                databaseConfigRequest.username(),
                databaseConfigRequest.password(),
                databaseConfigRequest.host(),
                databaseConfigRequest.databaseName(),
                databaseConfigRequest.port()
        );
    }

    private static ServerConfig toServerConfiguration(
            ServerConfigRequest serverConfigRequest) {

        return new ServerConfig(
                serverConfigRequest.port(),
                serverConfigRequest.contextPath()
        );
    }

    private static ApplicationConfig toApplicationConfiguration(
            ApplicationConfigRequest applicationConfigRequest) {

        return new ApplicationConfig(
                applicationConfigRequest.applicationName(),
                applicationConfigRequest.activeProfile()
        );
    }
}
