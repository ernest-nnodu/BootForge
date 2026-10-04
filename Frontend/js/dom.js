
const form = document.getElementById("configuration-form");
const applicationNameInputField = document.getElementById("application-name");
const activeProfileInputField = document.getElementById("active-profile");
const serverPortInputField = document.getElementById("server-port");
const contextPathInputField = document.getElementById("context-path");
const databaseTypeSelectField = document.getElementById("database-type");
const databaseNameInputField = document.getElementById("database-name");
const databasePortInputField = document.getElementById("database-port");
const databaseHostInputField = document.getElementById("database-host");
const databaseUsernameInputField = document.getElementById("database-username");
const databasePasswordInputField = document.getElementById("database-password");
const ddlAutoSelectField = document.getElementById("ddl-auto");
const showSqlSelectField = document.getElementById("show-sql");
const outputFormatSelectField = document.getElementById("output-format");
const generateConfigButton = document.getElementById("generate-config-btn");
const generatedConfigTextarea = document.getElementById("generated-config");

function readForm() {

    return {
        applicationName: applicationNameInputField.value,
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
    generatedConfigTextarea.textContent = config;
}

form.addEventListener("submit", (event) => {
    event.preventDefault();
});

export {readForm, displayGeneratedConfig, form, generateConfigButton};


