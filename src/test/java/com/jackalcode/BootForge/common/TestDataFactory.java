package com.jackalcode.BootForge.common;

import com.jackalcode.BootForge.domain.enums.DatabaseType;
import com.jackalcode.BootForge.domain.enums.OutputFormat;
import com.jackalcode.BootForge.dto.*;
import com.jackalcode.BootForge.mapper.RequestProps;

public class TestDataFactory {

    public static GenerateConfigRequest generateConfigRequest(RequestProps requestProps) {

        return new GenerateConfigRequest(
                new ApplicationConfigRequest(requestProps.getApplicationName(), requestProps.getActiveProfile()),
                new ServerConfigRequest(requestProps.getServerPort(), requestProps.getContextPath()),
                new DatabaseConfigRequest(requestProps.getDatabaseType(), requestProps.getUsername(),
                        requestProps.getPassword(), requestProps.getHost(), requestProps.getDatabaseName(),
                        requestProps.getDatabasePort()),
                new JpaConfigRequest(requestProps.getDdlAuto(), requestProps.getShowSql(), requestProps.getOpenInView()),
                new HikariConfigRequest(requestProps.getMaximumPoolSize(), requestProps.getMinimumIdle(), requestProps.getConnectionTimeout()),
                new LoggingConfigRequest(requestProps.getRootLevel(), requestProps.getSpringLevel()),
                new ActuatorConfigRequest(requestProps.getExposedEndpoints(), requestProps.getShowHealthDetails()),
                requestProps.getOutputFormat()
        );
    }

    public static GenerateConfigRequest validRequest() {

        ApplicationConfigRequest app = new ApplicationConfigRequest(
                "bootforge",
                "prod");

        ServerConfigRequest server = new ServerConfigRequest(
                8081,
                "/api");

        DatabaseConfigRequest db = new DatabaseConfigRequest(
                DatabaseType.POSTGRESQL,
                "user",
                "secret",
                "localhost",
                "appdb",
                5432);

        return new GenerateConfigRequest(
                app,
                server,
                db,
                new JpaConfigRequest(null, null, null),
                new HikariConfigRequest(null, null, null),
                new LoggingConfigRequest(null, null),
                new ActuatorConfigRequest(null, null),
                OutputFormat.PROPERTIES);
    }

    public static GenerateConfigRequest requestWIthFieldsMissing() {

        ApplicationConfigRequest app = new ApplicationConfigRequest(
                "bootforge",
                "prod");

        ServerConfigRequest server = new ServerConfigRequest(
                null,
                "/api");

        DatabaseConfigRequest db = new DatabaseConfigRequest(
                DatabaseType.POSTGRESQL,
                "user",
                "secret",
                "localhost",
                "appdb",
                5432);

        return new GenerateConfigRequest(
                app,
                server,
                db,
                new JpaConfigRequest(null, null, null),
                new HikariConfigRequest(null, null, null),
                new LoggingConfigRequest(null, null),
                new ActuatorConfigRequest(null, null),
                OutputFormat.PROPERTIES);
    }

    public static GenerateConfigRequest invalidRequest() {

        return new GenerateConfigRequest(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                OutputFormat.YAML
        );
    }
}
