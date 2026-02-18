package com.jackalcode.BootForge.formatter;

import com.jackalcode.BootForge.domain.enums.DatabaseType;
import com.jackalcode.BootForge.domain.model.*;
import com.jackalcode.BootForge.formatter.util.FormatterUtil;
import org.springframework.stereotype.Component;

@Component
public class PropertiesFormatter implements ConfigFormatter {

    @Override
    public String format(Configuration configuration) {

        return """
                #--------------------CONFIGURATION--------------------#
                
                %s
                %s
                %s
                %s
                %s
                %s
                %s
                
                #--------------------END OF CONFIGURATION--------------------#
                """
                .formatted(
                        formatApplicationConfig(configuration.applicationConfig()),
                        formatServerConfig(configuration.serverConfig()),
                        formatDatabaseConfig(configuration.databaseConfig()),
                        formatJpaConfig(configuration.jpaConfig(), configuration.databaseConfig().databaseType()),
                        formatHikariConfig(configuration.hikariConfig()),
                        formatLoggingConfig(configuration.loggingConfig()),
                        formatActuatorConfig(configuration.actuatorConfig())
                );
    }

    private String formatApplicationConfig(ApplicationConfig applicationConfig) {

        return """
                #----------Application Configuration----------#
                spring.application.name=%s
                spring.profiles.active=%s
                
                """
                .formatted(
                        applicationConfig.applicationName(),
                        applicationConfig.activeProfile()
                );
    }

    private String formatServerConfig(ServerConfig serverConfig) {
        return """
                #----------Server Configuration----------#
                server.port=%s
                server.servlet.context-path=%s
                
                """
                .formatted(
                        serverConfig.port(),
                        serverConfig.contextPath()
                );
    }

    private String formatDatabaseConfig(DatabaseConfig databaseConfig) {

        return """
                #----------Database Configuration----------#
                spring.datasource.url=%s
                spring.datasource.username=%s
                spring.datasource.password=%s
                
                """
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

    private String formatJpaConfig(JpaConfig jpaConfig, DatabaseType databaseType) {

        return """
                #----------JPA Configuration----------#
                spring.jpa.hibernate.ddl-auto=%s
                spring.jpa.database-platform=org.hibernate.dialect.%s
                spring.jpa.show-sql=%s
                spring.jpa.open-in-view=%s
                
                """
                .formatted(
                        jpaConfig.ddlAuto().toString().toLowerCase(),
                        FormatterUtil.generateSQLDialect(databaseType),
                        jpaConfig.showSql(),
                        jpaConfig.openInView()
                );
    }

    private String formatHikariConfig(HikariConfig hikariConfig) {

        return """
                #----------Hikari Configuration----------#
                spring.datasource.hikari.maximum-pool-size=%d
                spring.datasource.hikari.minimum-idle=%d
                spring.datasource.hikari.connection-timeout=%d
                
                """
                .formatted(
                        hikariConfig.maximumPoolSize(),
                        hikariConfig.minimumIdle(),
                        hikariConfig.connectionTimeout()
                );
    }

    private String formatLoggingConfig(LoggingConfig loggingConfig) {

        return """
                #----------Logging Configuration----------#
                logging.level.root=%s
                logging.level.org.springframework=%s
                
                """
                .formatted(
                  loggingConfig.rootLevel().toString().toLowerCase(),
                  loggingConfig.springLevel().toString().toLowerCase()
                );
    }

    private String formatActuatorConfig(ActuatorConfig actuatorConfig) {

        return """
                #----------Actuator Configuration----------#
                management.endpoints.web.exposure.include=%s
                management.endpoint.health.show-details=%s
                
                """
                .formatted(
                        actuatorConfig.exposedEndpoints(),
                        actuatorConfig.showHealthDetails().toString().toLowerCase()
                );
    }
}
