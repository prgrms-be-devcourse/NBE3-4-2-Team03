export async function fetchDeliveryList() {
    const response = await fetch('http://localhost:8080/delivery', {
        credentials: 'include'
    });

    if (!response.ok) {
        throw new Error('Failed to fetch deliveries');
    }

    return response.json();
}

export async function updateDelivery(deliveryId, address) {
    const response = await fetch(`http://localhost:8080/delivery/${deliveryId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify({ address }),
    });

    if (!response.ok) {
        throw new Error('Failed to update delivery status');
    }

    return response.json();
}

export async function deleteDelivery(deliveryId) {
    const response = await fetch(`http://localhost:8080/delivery/${deliveryId}`, {
        method: 'DELETE',
        credentials: 'include'
    });

    if (!response.ok) {
        throw new Error('Failed to delete delivery');
    }

    return response.json();
}