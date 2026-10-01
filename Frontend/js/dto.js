
const applicationConfigRequest = createApplicationConfigRequest(
    "BootForge", "dev");

const serverConfigRequest = createServerConfigRequest(8080, "/api");

const databaseProperties = {
    databaseType: "POSTGRESQL",
    username: "user",
    password: "password",
    host: "localhost",
    databaseName: "bootforge_db",
    port: 5432
};

const databaseConfigRequest = createDatabaseConfigRequest(databaseProperties);

const jpaProperties = {
    ddlAuto: "update",
    showSql: true,
    openInView: true
};

const jpaConfigRequest = createJpaConfigRequest(jpaProperties);

const hikariConfigRequest = createHikariConfigRequest(10, 2, 30000);

const loggingConfigRequest = createLoggingConfigRequest("INFO", "INFO");

const actuatorConfigRequest = createActuatorConfigRequest("health, info, metrics", "ALWAYS");

const generateConfigRequest = createGenerateConfigRequest(
    applicationConfigRequest,
    serverConfigRequest,
    databaseConfigRequest,
    jpaConfigRequest,
    hikariConfigRequest,
    loggingConfigRequest,
    actuatorConfigRequest,
    "YAML"
);

function createApplicationConfigRequest(applicationName, activeProfile) {
    return {
        applicationName,
        activeProfile
    };
}

function createServerConfigRequest(port, contextPath) {
    return {
        port,
        contextPath
    };
}

function createDatabaseConfigRequest({databaseType, username, password, host, databaseName, port}) {
    return {
        databaseType,
        username,
        password,
        host,
        databaseName,
        port
    };
}

function createJpaConfigRequest({ddlAuto, showSql, openInView}) {
    return {
        ddlAuto,
        showSql,
        openInView
    };
}

function createHikariConfigRequest(maximumPoolSize, minimumIdle, connectionTimeout) {
    return {
        maximumPoolSize,
        minimumIdle,
        connectionTimeout
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

function createGenerateConfigRequest(
    applicationConfigRequest,
    serverConfigRequest,
    databaseConfigRequest,
    jpaConfigRequest,
    hikariConfigRequest,
    loggingConfigRequest,
    actuatorConfigRequest,
    outputFormat) {

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







