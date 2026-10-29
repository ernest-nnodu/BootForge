import {configForm, displayGeneratedConfig, generateConfigButton, copyToClipboardButton, readForm} from "./dom.js";
import {createGenerateConfigRequest} from "./dto.js";
import {generateConfiguration} from "./api.js";

let generatedConfig;

async function processConfig(requestData) {

    return await generateConfiguration(requestData);
}

configForm.addEventListener("submit", (event) => {

    event.preventDefault(); //prevent the default form submission behavior
    const formData = readForm();
    const requestData = createGenerateConfigRequest(formData);

    processConfig(requestData)
        .then((config) => {
            generatedConfig = config;
            displayGeneratedConfig(generatedConfig.content, generatedConfig.format);
        } )
        .catch((error) => {
            displayGeneratedConfig(`Error: ${error.message}`);
        })
        .finally(() => generateConfigButton.disabled = false);
    generateConfigButton.disabled = true;
});

copyToClipboardButton.addEventListener("click", async () => {

    try {
        if (!generatedConfig) {
            return;
        }

        await navigator.clipboard.writeText(generatedConfig.content);
        copyToClipboardButton.textContent = "Copied!"

        setTimeout(() => copyToClipboardButton.textContent = "Copy to clipboard", 2000);
    } catch (error) {
        console.error("Failed to copy to configuration", error);
    }
});