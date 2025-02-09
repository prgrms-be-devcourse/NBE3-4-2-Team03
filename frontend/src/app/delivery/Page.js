'use client';
import { useState, useEffect } from 'react';

export default function DeliveryManagement() {
    const [deliveries, setDeliveries] = useState([]);
    const [editDelivery, setEditDelivery] = useState(null);
    const [newAddress, setNewAddress] = useState('');
    const [showCreateForm, setShowCreateForm] = useState(false);
    const [createFormData, setCreateFormData] = useState({
        address: '',
        estimateId: ''
    });

    // ... 기존 useEffect 코드 유지 ...

    const handleCreate = async (e) => {
        e.preventDefault();
        if (!createFormData.address.trim() || !createFormData.estimateId) {
            alert('모든 필드를 입력해주세요.');
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/delivery?id=${createFormData.estimateId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'  // Accept 헤더 추가
                },
                credentials: 'include',  // 필요한 경우 쿠키 포함
                body: JSON.stringify({ address: createFormData.address })
            });

            if (!response.ok) {
                const errorData = await response.text();
                throw new Error(errorData || '배송 생성 실패');
            }

            // ... existing code ...
        } catch (error) {
            console.error('배송 생성 오류:', error);
            alert(`배송 생성 중 오류가 발생했습니다: ${error.message}`);
        }
    };

    // ... 기존 handleDelete, handleUpdate 코드 유지 ...

    return (
        <div className="min-h-screen p-8 dark:bg-gray-900">
            <div className="flex justify-between items-center mb-8">
                <h1 className="text-2xl font-bold dark:text-white">배송 관리</h1>
                <button
                    onClick={() => setShowCreateForm(true)}
                    className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
                >
                    새 배송 생성
                </button>
            </div>

            {/* 배송 생성 폼 */}
            {showCreateForm && (
                <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
                    <div className="bg-white dark:bg-gray-800 rounded-lg p-6 max-w-md w-full">
                        <h3 className="text-lg font-semibold mb-4 dark:text-white">새 배송 생성</h3>
                        <form onSubmit={handleCreate} className="space-y-4">
                            <div>
                                <label className="block text-gray-700 dark:text-gray-300 mb-2">
                                    견적 ID
                                </label>
                                <input
                                    type="number"
                                    value={createFormData.estimateId}
                                    onChange={(e) => setCreateFormData({
                                        ...createFormData,
                                        estimateId: e.target.value
                                    })}
                                    className="w-full px-3 py-2 border rounded dark:bg-gray-700 dark:border-gray-600 dark:text-white"
                                    placeholder="견적 ID를 입력하세요"
                                />
                            </div>
                            <div>
                                <label className="block text-gray-700 dark:text-gray-300 mb-2">
                                    배송 주소
                                </label>
                                <input
                                    type="text"
                                    value={createFormData.address}
                                    onChange={(e) => setCreateFormData({
                                        ...createFormData,
                                        address: e.target.value
                                    })}
                                    className="w-full px-3 py-2 border rounded dark:bg-gray-700 dark:border-gray-600 dark:text-white"
                                    placeholder="배송 주소를 입력하세요"
                                />
                            </div>
                            <div className="flex justify-end gap-2 mt-4">
                                <button
                                    type="button"
                                    onClick={() => setShowCreateForm(false)}
                                    className="px-4 py-2 bg-gray-500 text-white rounded hover:bg-gray-600"
                                >
                                    취소
                                </button>
                                <button
                                    type="submit"
                                    className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
                                >
                                    생성
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}

            {/* 기존 배송 목록 */}
            <div className="space-y-4">
                {/* ... 기존 배송 목록 코드 유지 ... */}
            </div>
        </div>
    );
}