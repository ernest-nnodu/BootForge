package com.jackalcode.BootForge.mapper;

import com.jackalcode.BootForge.domain.model.*;
import com.jackalcode.BootForge.dto.*;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationMapper {

    public Configuration toDomain(GenerateConfigRequest configRequest) {

        return new Configuration(
                mapApplication(configRequest.applicationConfigRequest()),
                mapServer(configRequest.serverConfigRequest()),
                mapDatabase(configRequest.databaseConfigRequest()),
                mapJpa(configRequest.jpaConfigRequest()),
                mapHikari(configRequest.hikariConfigRequest()),
                mapLogging(configRequest.loggingConfigRequest()),
                mapActuator(configRequest.actuatorConfigRequest())
        );
    }

    private ActuatorConfig mapActuator(ActuatorConfigRequest actuatorConfigRequest) {

        //If the request is null, the domain model will set default values
        if (actuatorConfigRequest == null) {
            return new ActuatorConfig(null, null);
        }

        return new ActuatorConfig(
                actuatorConfigRequest.exposedEndpoints(),
                actuatorConfigRequest.showHealthDetails()
        );
    }

    private LoggingConfig mapLogging(LoggingConfigRequest loggingConfigRequest) {

        //If the request is null, the domain model will set default values
        if (loggingConfigRequest == null) {
            return new LoggingConfig(null, null);
        }

        return new LoggingConfig(
                loggingConfigRequest.rootLevel(),
                loggingConfigRequest.springLevel()
        );
    }

    private HikariConfig mapHikari(HikariConfigRequest hikariConfigRequest) {

        //If the request is null, the domain model will set default values
        if (hikariConfigRequest == null) {
            return new HikariConfig(null, null, null);
        }

        return new HikariConfig(
                hikariConfigRequest.maximumPoolSize(),
                hikariConfigRequest.minimumIdle(),
                hikariConfigRequest.connectionTimeout()
        );
    }

    private JpaConfig mapJpa(JpaConfigRequest jpaConfigRequest) {

        //If the request is null, the domain model will set default values
        if (jpaConfigRequest == null) {
            return new JpaConfig(null, null, null);
        }

        return new JpaConfig(
                jpaConfigRequest.ddlAuto(),
                jpaConfigRequest.showSql(),
                jpaConfigRequest.openInView()
        );
    }

    private DatabaseConfig mapDatabase(DatabaseConfigRequest databaseConfigRequest) {

        return new DatabaseConfig(
                databaseConfigRequest.databaseType(),
                databaseConfigRequest.username(),
                databaseConfigRequest.password(),
                databaseConfigRequest.host(),
                databaseConfigRequest.databaseName(),
                databaseConfigRequest.port()
        );
    }

    private ServerConfig mapServer(ServerConfigRequest serverConfigRequest) {

        return new ServerConfig(
                serverConfigRequest.port(),
                serverConfigRequest.contextPath()
        );
    }

    private ApplicationConfig mapApplication(ApplicationConfigRequest applicationConfigRequest) {

        return new ApplicationConfig(
                applicationConfigRequest.applicationName(),
                applicationConfigRequest.activeProfile()
        );
    }
}
