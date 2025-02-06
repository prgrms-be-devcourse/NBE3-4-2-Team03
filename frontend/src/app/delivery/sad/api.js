import axios from 'axios';

export default async function handler(req, res) {
    if (req.method === 'POST') {
        const { address } = req.body;
        const { id } = req.query;
        try {
            const response = await axios.post('http://백엔드서버주소/delivery', { address }, { params: { id } });
            res.status(201).json({ message: '주문이 완료되었습니다.', data: response.data });
        } catch (error) {
            res.status(500).json({ error: '주문 처리 중 오류가 발생했습니다.' });
        }
    } else {
        res.setHeader('Allow', ['POST']);
        res.status(405).end(`Method ${req.method} Not Allowed`);
    }
}
