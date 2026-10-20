
async function generateConfiguration(requestData) {

    const url = "http://localhost:8000/api/v1/configurations/generate";
    const body = JSON.stringify(requestData);

    const response = await fetch(url, {
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
