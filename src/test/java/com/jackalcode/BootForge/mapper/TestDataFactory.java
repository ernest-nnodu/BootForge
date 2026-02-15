package com.jackalcode.BootForge.mapper;

import com.jackalcode.BootForge.domain.enums.DatabaseType;
import com.jackalcode.BootForge.dto.*;

public class TestDataFactory {

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
