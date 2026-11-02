
async function generateConfiguration(requestData) {

    const API_BASE_URL = "https://bootforge.onrender.com"
    const API_URL = `${API_BASE_URL}/api/v1/configurations/generate`;
    const body = JSON.stringify(requestData);

    const response = await fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: body
    });

    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`HTTP error! status: ${response.status} message: ${errorData.message}`);
    }

    return await response.json();
}

export { generateConfiguration };
