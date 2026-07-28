package com.jackalcode.BootForge.common;

import com.jackalcode.BootForge.dto.*;

public class GenerateConfigRequestTestHelper {

    public static GenerateConfigRequest generateConfigRequest(RequestProps requestProps) {

        return new GenerateConfigRequest(
                getApplicationConfigRequest(requestProps),
                getServerConfigRequest(requestProps),
                getDatabaseConfigRequest(requestProps),
                getJpaConfigRequest(requestProps),
                getHikariConfigRequest(requestProps),
                getLoggingConfigRequest(requestProps),
                getActuatorConfigRequest(requestProps),
                requestProps.getOutputFormat()
        );
    }

    private static ActuatorConfigRequest getActuatorConfigRequest(RequestProps requestProps) {
        return new ActuatorConfigRequest(requestProps.getExposedEndpoints(), requestProps.getShowHealthDetails());
    }

    private static LoggingConfigRequest getLoggingConfigRequest(RequestProps requestProps) {
        return new LoggingConfigRequest(requestProps.getRootLevel(), requestProps.getSpringLevel());
    }

    private static HikariConfigRequest getHikariConfigRequest(RequestProps requestProps) {
        return new HikariConfigRequest(requestProps.getMaximumPoolSize(), requestProps.getMinimumIdle(), requestProps.getConnectionTimeout());
    }

    private static JpaConfigRequest getJpaConfigRequest(RequestProps requestProps) {
        return new JpaConfigRequest(requestProps.getDdlAuto(), requestProps.getShowSql(), requestProps.getOpenInView());
    }

    private static DatabaseConfigRequest getDatabaseConfigRequest(RequestProps requestProps) {
        return new DatabaseConfigRequest(requestProps.getDatabaseType(), requestProps.getUsername(),
                requestProps.getPassword(), requestProps.getHost(), requestProps.getDatabaseName(),
                requestProps.getDatabasePort());
    }

    private static ServerConfigRequest getServerConfigRequest(RequestProps requestProps) {
        return new ServerConfigRequest(requestProps.getServerPort(), requestProps.getContextPath());
    }

    private static ApplicationConfigRequest getApplicationConfigRequest(RequestProps requestProps) {

        return new ApplicationConfigRequest(requestProps.getApplicationName(), requestProps.getActiveProfile());
    }
}
