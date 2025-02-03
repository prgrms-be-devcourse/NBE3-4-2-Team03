// estimaterequest/page2.js

import { useEffect, useState } from 'react';
import { getEstimateRequests } from './api';
import Layout from './layout';

export default function ViewEstimateRequests() {
    const [requests, setRequests] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await getEstimateRequests();
                setRequests(data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };
        fetchData();
    }, []);

    return (
        <Layout>
            <h1>Estimate Requests</h1>
            <ul>
                {requests.map((request) => (
                    <li key={request.id}>
                        <p>Purpose: {request.purpose}</p>
                        <p>Budget: {request.budget}</p>
                        <p>Other Request: {request.otherRequest}</p>
                        <p>Created At: {new Date(request.createDate).toLocaleString()}</p>
                    </li>
                ))}
            </ul>
        </Layout>
    );
}
