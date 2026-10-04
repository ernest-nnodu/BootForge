import {readForm, generateConfigButton, displayGeneratedConfig} from "./dom.js";
import { createGenerateConfigRequest } from "./dto.js";

generateConfigButton.addEventListener("click", () => {
    const formData = readForm();
    const requestData = createGenerateConfigRequest(formData);
    displayGeneratedConfig(JSON.stringify(requestData, null, 2));
});
