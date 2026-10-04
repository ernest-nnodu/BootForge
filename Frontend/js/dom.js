
let projectNameInputField = document.getElementById("project-name");
let activeProfileInputField = document.getElementById("active-profile");
let serverPortInputField = document.getElementById("server-port");
let contextPathInputField = document.getElementById("context-path");
let databaseTypeSelectField = document.getElementById("database-type");
let databaseNameInputField = document.getElementById("database-name");
let databasePortInputField = document.getElementById("database-port");
let databaseHostInputField = document.getElementById("database-host");
let databaseUsernameInputField = document.getElementById("database-username");
let databasePasswordInputField = document.getElementById("database-password");
let ddlAutoSelectField = document.getElementById("ddl-auto");
let showSqlSelectField = document.getElementById("show-sql");
let outputFormatSelectField = document.getElementById("output-format");
let generatedConfigTextarea = document.getElementById("generated-config");

function readForm() {

    return {
        projectName: projectNameInputField.value,
        activeProfile: activeProfileInputField.value,
        serverPort: serverPortInputField.value,
        contextPath: contextPathInputField.value,
        databaseType: databaseTypeSelectField.value,
        databaseName: databaseNameInputField.value,
        databasePort: databasePortInputField.value,
        databaseHost: databaseHostInputField.value,
        databaseUsername: databaseUsernameInputField.value,
        databasePassword: databasePasswordInputField.value,
        ddlAuto: ddlAutoSelectField.value,
        showSql: showSqlSelectField.value,
        outputFormat: outputFormatSelectField.value
    };
}

function displayGeneratedConfig(config) {
    generatedConfigTextarea.value = config;
}

export {readForm, displayGeneratedConfig};


