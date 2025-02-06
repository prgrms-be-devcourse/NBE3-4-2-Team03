import { useState } from 'react';
import axios from 'axios';

const OrderForm = ({ estimateId }) => {
    const [address, setAddress] = useState('');
    const [message, setMessage] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            const response = await axios.post(`/api/delivery?id=${estimateId}`, {
                address
            });

            if (response.status === 201) {
                setMessage('주문이 완료되었습니다.');
            }
        } catch (error) {
            setMessage('주문 중 오류가 발생했습니다.');
            console.error('Error creating delivery:', error);
        }
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <label>
                    주소:
                    <input
                        type="text"
                        value={address}
                        onChange={(e) => setAddress(e.target.value)}
                        required
                    />
                </label>
                <button type="submit">주문 완료</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default OrderForm;
