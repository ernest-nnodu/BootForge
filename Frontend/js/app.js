import {readForm, displayGeneratedConfig, configForm} from "./dom.js";
import { createGenerateConfigRequest } from "./dto.js";

configForm.addEventListener("submit", (event) => {

    event.preventDefault(); //prevent the default form submission behavior
    const formData = readForm();
    const requestData = createGenerateConfigRequest(formData);
    displayGeneratedConfig(JSON.stringify(requestData, null, 2));
});