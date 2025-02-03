// estimaterequest/api.js

export async function createEstimateRequest(data) {
    const response = await fetch('http://localhost:8080/estimate/request', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });
    return response.json();
}

export async function getEstimateRequests() {
    const response = await fetch('http://localhost:8080/estimate/request', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });
    return response.json();
}
