import {configForm, displayGeneratedConfig, generateConfigButton, readForm} from "./dom.js";
import {createGenerateConfigRequest} from "./dto.js";
import {generateConfiguration} from "./api.js";

async function processConfig(requestData) {

    return await generateConfiguration(requestData);
}

configForm.addEventListener("submit", (event) => {

    event.preventDefault(); //prevent the default form submission behavior
    const formData = readForm();
    const requestData = createGenerateConfigRequest(formData);
    processConfig(requestData)
        .then((config) => displayGeneratedConfig(config.content))
        .catch((error) => {
            displayGeneratedConfig(`Error: ${error.message}`);
        })
        .finally(() => generateConfigButton.disabled = false);
    generateConfigButton.disabled = true;
});