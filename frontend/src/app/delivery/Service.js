import { NextApiRequest, NextApiResponse } from 'next';
import axios from 'axios';

export default async function handler(req = NextApiRequest, res = NextApiResponse) {
    if (req.method === 'POST') {
        const { address } = req.body;
        const estimateId = req.query.id;

        try {
            const response = await axios.post('http://localhost:8080/api/delivery', {
                address,
                id: estimateId
            });

            if (response.status === 201) {
                res.status(201).json({ message: '주문이 완료되었습니다.' });
            } else {
                res.status(response.status).json({ message: '오류가 발생했습니다.' });
            }
        } catch (error) {
            console.error('Error creating delivery:', error);
            res.status(500).json({ message: '서버 오류가 발생했습니다.' });
        }
    } else {
        res.setHeader('Allow', ['POST']);
        res.status(405).end(`Method ${req.method} Not Allowed`);
    }
}
