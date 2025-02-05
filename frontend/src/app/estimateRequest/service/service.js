export async function createEstimateRequest(purpose, budget, otherRequest) {
    const response = await fetch("http://localhost:8080/estimate/request", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ purpose, budget, otherRequest }),
    });

    if (!response.ok) {
        throw new Error("Failed to create estimate request");
    }
}
