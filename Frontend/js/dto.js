
function createApplicationConfigRequest(applicationName, activeProfile) {
    return {
        applicationName,
        activeProfile
    };
}

function createServerConfigRequest(port, contextPath) {
    return {
        port: Number(port),
        contextPath
    };
}

function createDatabaseConfigRequest(
    {databaseType, databaseUsername, databasePassword, databaseHost, databaseName, databasePort}) {

    return {
        databaseType: databaseType,
        username: databaseUsername,
        password: databasePassword,
        host: databaseHost,
        databaseName: databaseName,
        port: Number(databasePort)
    };
}

function createJpaConfigRequest({ddlAuto, showSql, openInView}) {
    return {
        ddlAuto,
        showSql: Boolean(showSql),
        openInView: Boolean(openInView),
    };
}

function createHikariConfigRequest(maximumPoolSize, minimumIdle, connectionTimeout) {
    return {
        maximumPoolSize: Number(maximumPoolSize),
        minimumIdle: Number(minimumIdle),
        connectionTimeout: Number(connectionTimeout)
    };
}

function createLoggingConfigRequest(rootLevel, springLevel) {
    return {
        rootLevel,
        springLevel
    };
}

function createActuatorConfigRequest(exposedEndpoints, showHealthDetails) {
    return {
        exposedEndpoints,
        showHealthDetails
    };
}

function createGenerateConfigRequest(configurationProperties) {

    const applicationConfigRequest =
        createApplicationConfigRequest(configurationProperties.applicationName, configurationProperties.activeProfile);

    const serverConfigRequest =
        createServerConfigRequest(configurationProperties.serverPort, configurationProperties.contextPath);

    const databaseConfigRequest = createDatabaseConfigRequest(configurationProperties);

    const jpaConfigRequest = createJpaConfigRequest(configurationProperties);

    const hikariConfigRequest = createHikariConfigRequest(10, 2, 30000);

    const loggingConfigRequest = createLoggingConfigRequest("INFO", "INFO");

    const actuatorConfigRequest =
        createActuatorConfigRequest("health, info, metrics", "ALWAYS");

    const outputFormat = configurationProperties.outputFormat;

    return {
        applicationConfigRequest,
        serverConfigRequest,
        databaseConfigRequest,
        jpaConfigRequest,
        hikariConfigRequest,
        loggingConfigRequest,
        actuatorConfigRequest,
        outputFormat
    }
}

export {createGenerateConfigRequest};






