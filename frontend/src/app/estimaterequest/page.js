// estimaterequest/page.js

import { useState } from 'react';
import { createEstimateRequest } from './api';
import Layout from './layout';

export default function EstimateRequestPage() {
    const [purpose, setPurpose] = useState('');
    const [budget, setBudget] = useState('');
    const [otherRequest, setOtherRequest] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();
        const requestData = {
            purpose,
            budget: parseInt(budget),
            otherRequest,
        };
        try {
            const result = await createEstimateRequest(requestData);
            console.log('Success:', result);
        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <Layout>
            <h1>Estimate Request</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Purpose:</label>
                    <input
                        type="text"
                        value={purpose}
                        onChange={(e) => setPurpose(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Budget:</label>
                    <input
                        type="number"
                        value={budget}
                        onChange={(e) => setBudget(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Other Request:</label>
                    <input
                        type="text"
                        value={otherRequest}
                        onChange={(e) => setOtherRequest(e.target.value)}
                    />
                </div>
                <button type="submit">Submit</button>
            </form>
        </Layout>
    );
}
