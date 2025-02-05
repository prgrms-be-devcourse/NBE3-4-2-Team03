// components/DeliveryForm.js
"use client";

import React, { useState } from 'react';
import axios from 'axios';

const DeliveryForm = () => {
    const [address, setAddress] = useState('');
    const [estimateId, setEstimateId] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();

        const data = {
            address,
            estimate: { id: estimateId }
        };

        try {
            const response = await axios.post('/api/delivery', data);
            alert(response.data);
        } catch (error) {
            console.error('There was an error creating the delivery!', error);
        }
    };

    return (
        <form onSubmit={handleSubmit} style={styles.form}>
            <label style={{ ...styles.label, color: 'black' }}>
                Address:
                <input
                    type="text"
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                    required
                    style={styles.input}
                />
            </label>
            <br />
            <label style={{ ...styles.label, color: 'black' }}>
                Estimate ID:
                <input
                    type="text"
                    value={estimateId}
                    onChange={(e) => setEstimateId(e.target.value)}
                    required
                    style={styles.input}
                />
            </label>
            <br />
            <button type="submit" style={styles.button}>Create Delivery</button>
        </form>
    );
};

const styles = {
    form: {
        backgroundColor: '#f2f2f2',
        padding: '20px',
        borderRadius: '5px',
        maxWidth: '400px',
        margin: '0 auto'
    },
    label: {
        display: 'block',
        marginBottom: '10px'
    },
    input: {
        width: '100%',
        padding: '8px',
        margin: '5px 0 10px',
        borderRadius: '3px',
        border: '1px solid #ccc',
        color: 'black' // 입력 필드의 텍스트 색상을 검정색으로 설정
    },
    button: {
        backgroundColor: '#4CAF50',
        color: 'white',
        padding: '10px 20px',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer'
    }
};

export default DeliveryForm;
