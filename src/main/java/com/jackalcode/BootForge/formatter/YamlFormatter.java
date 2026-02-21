package com.jackalcode.BootForge.formatter;

import com.jackalcode.BootForge.domain.enums.DatabaseType;
import com.jackalcode.BootForge.domain.model.*;
import com.jackalcode.BootForge.formatter.util.FormatterUtil;
import org.springframework.stereotype.Component;

@Component
public class YamlFormatter implements ConfigFormatter {

    private static final String INDENT = "  ";

    @Override
    public String format(Configuration configuration) {

        StringBuilder yaml = new StringBuilder();

        yaml.append("spring:\n");

        // ---- Application ----
        yaml.append(indent(formatApplicationConfig(configuration.applicationConfig()), 1));

        // ---- Datasource ----
        yaml.append(indent("datasource:\n", 1));
        yaml.append(indent(formatDatabaseConfig(configuration.databaseConfig()), 2));
        yaml.append(indent(formatHikariConfig(configuration.hikariConfig()), 2));

        // ---- JPA ----
        yaml.append(indent(formatJpaConfig(
                configuration.jpaConfig(),
                configuration.databaseConfig().databaseType()
        ), 1));

        // ---- Server ----
        yaml.append(formatServerConfig(configuration.serverConfig()));

        // ---- Logging ----
        yaml.append(formatLoggingConfig(configuration.loggingConfig()));

        // ---- Actuator ----
        yaml.append(formatActuatorConfig(configuration.actuatorConfig()));

        return yaml.toString();
    }

    private String formatApplicationConfig(ApplicationConfig config) {
        return """
application:
  name: %s
  profiles:
    active: %s
""".formatted(
                config.applicationName(),
                config.activeProfile()
        );
    }

    private String formatDatabaseConfig(DatabaseConfig config) {
        return """
url: %s
username: %s
password: %s
""".formatted(
                FormatterUtil.generateDatasourceUrl(
                        config.databaseType(),
                        config.databaseName(),
                        config.host(),
                        config.port()
                ),
                config.username(),
                config.password()
        );
    }

    private String formatHikariConfig(HikariConfig config) {
        return """
hikari:
  maximum-pool-size: %d
  minimum-idle: %d
  connection-timeout: %d
""".formatted(
                config.maximumPoolSize(),
                config.minimumIdle(),
                config.connectionTimeout()
        );
    }

    private String formatJpaConfig(JpaConfig config, DatabaseType databaseType) {
        return """
jpa:
  hibernate:
    ddl-auto: %s
  database:
    platform: %s
  show-sql: %s
  open-in-view: %s
""".formatted(
                config.ddlAuto().toString().toLowerCase(),
                FormatterUtil.generateSQLDialect(databaseType),
                config.showSql(),
                config.openInView()
        );
    }

    private String formatServerConfig(ServerConfig config) {
        return """
server:
  port: %d
  servlet:
    context-path: %s
""".formatted(
                config.port(),
                config.contextPath()
        );
    }

    private String formatLoggingConfig(LoggingConfig config) {
        return """
logging:
  level:
    root: %s
    org.springframework: %s
""".formatted(
                config.rootLevel().toString().toLowerCase(),
                config.springLevel().toString().toLowerCase()
        );
    }

    private String formatActuatorConfig(ActuatorConfig config) {
        return """
management:
  endpoints:
    web:
      exposure:
        include: %s
  endpoint:
    health:
      show-details: %s
""".formatted(
                config.exposedEndpoints(),
                config.showHealthDetails().toString().toLowerCase()
        );
    }

    private String indent(String block, int level) {
        String prefix = INDENT.repeat(level);
        return block.lines()
                .map(line -> line.isBlank() ? line : prefix + line)
                .reduce("", (a, b) -> a + b + "\n");
    }
}
