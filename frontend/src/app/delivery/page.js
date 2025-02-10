'use client';
import { useState, useEffect } from 'react';
import { fetchDeliveryList, updateDelivery, deleteDelivery } from './service/service';

export default function DeliveryPage() {
    const [deliveries, setDeliveries] = useState([]);
    const [error, setError] = useState(null);

    // 배송 목록 조회
    const fetchDeliveries = async () => {
        try {
            const data = await fetchDeliveryList();
            setDeliveries(data);
            setError(null); // 성공 시 에러 초기화
        } catch (error) {
            console.error('배송 목록 조회 실패:', error);
            setError('배송 목록을 불러오는데 실패했습니다. 잠시 후 다시 시도해주세요.');
            // 세션이 만료된 경우 로그인 페이지로 리다이렉트
            if (error.message.includes('다시 실행해주세요')) {
                window.location.href = '/login';
                return;
            }
        }
    };

    // 배송 삭제 처리
    const handleDeleteDelivery = async (id) => {
        try {
            await deleteDelivery(id);
            // 삭제 성공 시 목록 새로고침
            await fetchDeliveries();
        } catch (error) {
            console.error('배송 삭제 실패:', error);
            // 세션이 만료된 경우 로그인 페이지로 리다이렉트
            if (error.message.includes('다시 실행해주세요')) {
                window.location.href = '/login';
                return;
            }
            alert('배송 삭제에 실패했습니다. 잠시 후 다시 시도해주세요.');
        }
    };

    // 배송 수정 처리
    const handleUpdateDelivery = async (id, newAddress) => {
        try {
            await updateDelivery(id, newAddress);
            // 수정 성공 시 목록 새로고침
            await fetchDeliveries();
        } catch (error) {
            console.error('배송 수정 실패:', error);
            // 세션이 만료된 경우 로그인 페이지로 리다이렉트
            if (error.message.includes('다시 실행해주세요')) {
                window.location.href = '/login';
                return;
            }
            alert('배송 수정에 실패했습니다. 잠시 후 다시 시도해주세요.');
        }
    };

    useEffect(() => {
        fetchDeliveries();
    }, []);

    // 에러가 있는 경우 에러 메시지 표시
    if (error) {
        return (
            <div className="min-h-screen flex items-center justify-center">
                <div className="text-red-500">{error}</div>
            </div>
        );
    }

    return (
        <div className="min-h-screen p-8">
            <h1 className="text-2xl font-bold mb-8">배송 관리</h1>

            {/* 배송 목록 */}
            <div className="space-y-4">
                {deliveries.map((delivery) => (
                    <div key={delivery.id} className="bg-white p-6 rounded-lg shadow-sm">
                        <div className="flex justify-between items-center mb-4">
                            <span className="text-lg font-semibold">배송 #{delivery.id}</span>
                            <div className="space-x-2">
                                <button
                                    onClick={() => {
                                        const newAddress = prompt('새로운 배송 주소를 입력하세요:', delivery.address);
                                        if (newAddress) {
                                            handleUpdateDelivery(delivery.id, newAddress);
                                        }
                                    }}
                                    className="text-blue-500 hover:text-blue-700"
                                >
                                    수정
                                </button>
                                <button
                                    onClick={() => {
                                        if (window.confirm('정말 삭제하시겠습니까?')) {
                                            handleDeleteDelivery(delivery.id);
                                        }
                                    }}
                                    className="text-red-500 hover:text-red-700"
                                >
                                    삭제
                                </button>
                            </div>
                        </div>
                        <div className="grid grid-cols-2 gap-2 text-sm">
                            <div className="text-gray-600">배송 주소</div>
                            <div>{delivery.address}</div>
                            <div className="text-gray-600">상태</div>
                            <div>{delivery.status}</div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}