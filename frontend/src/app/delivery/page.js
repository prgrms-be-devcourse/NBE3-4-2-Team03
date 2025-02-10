'use client'

import { useState, useEffect } from "react";
import { fetchDeliveryList, updateDelivery, deleteDelivery } from "./service/service";

export default function DeliveryPage() {
    const [deliveries, setDeliveries] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    const [editingDelivery, setEditingDelivery] = useState(null);
    const [newAddress, setNewAddress] = useState("");

    useEffect(() => {
        loadDeliveries();
    }, []);

    const loadDeliveries = async () => {
        try {
            const data = await fetchDeliveryList();
            setDeliveries(data);
            setIsLoading(false);
        } catch (err) {
            setError(err.message);
            setIsLoading(false);
        }
    };

    const handleUpdateDelivery = async (deliveryId) => {
        try {
            await updateDelivery(deliveryId, newAddress);
            setEditingDelivery(null);
            setNewAddress("");
            loadDeliveries();
        } catch (err) {
            setError(err.message);
        }
    };

    const handleDeleteDelivery = async (deliveryId) => {
        if (window.confirm('정말로 이 배송을 취소하시겠습니까?')) {
            try {
                await deleteDelivery(deliveryId);
                loadDeliveries();
            } catch (err) {
                setError(err.message);
            }
        }
    };

    if (isLoading) {
        return <div className="text-center py-8">로딩중...</div>;
    }

    if (error) {
        return <div className="text-center py-8 text-red-500">{error}</div>;
    }

    return (
        <div className="container mx-auto px-4 py-8">
            <h1 className="text-2xl font-bold mb-6">배송 관리</h1>

            <div className="grid gap-6">
                {deliveries.length === 0 ? (
                    <div className="text-center py-8 text-gray-500">
                        배송 내역이 없습니다.
                    </div>
                ) : (
                    deliveries.map((delivery) => (
                        <div
                            key={delivery.id}
                            className="bg-white p-6 rounded-lg shadow-sm border border-gray-200"
                        >
                            <div className="flex justify-between items-start mb-4">
                                <div>
                                    <h3 className="font-semibold text-lg">배송 #{delivery.id}</h3>
                                    {editingDelivery === delivery.id ? (
                                        <div className="mt-2">
                                            <input
                                                type="text"
                                                value={newAddress}
                                                onChange={(e) => setNewAddress(e.target.value)}
                                                className="w-full p-2 border rounded"
                                                placeholder="새로운 배송 주소"
                                            />
                                            <div className="mt-2 flex gap-2">
                                                <button
                                                    onClick={() => handleUpdateDelivery(delivery.id)}
                                                    className="px-3 py-1 bg-blue-500 text-white rounded hover:bg-blue-600"
                                                >
                                                    저장
                                                </button>
                                                <button
                                                    onClick={() => {
                                                        setEditingDelivery(null);
                                                        setNewAddress("");
                                                    }}
                                                    className="px-3 py-1 bg-gray-500 text-white rounded hover:bg-gray-600"
                                                >
                                                    취소
                                                </button>
                                            </div>
                                        </div>
                                    ) : (
                                        <p className="text-gray-600 mt-1">{delivery.address}</p>
                                    )}
                                </div>
                            </div>

                            <div className="border-t pt-4">
                                <div className="flex gap-2 justify-end">
                                    {editingDelivery !== delivery.id && (
                                        <>
                                            <button
                                                onClick={() => {
                                                    setEditingDelivery(delivery.id);
                                                    setNewAddress(delivery.address);
                                                }}
                                                className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                                            >
                                                수정
                                            </button>
                                            <button
                                                onClick={() => handleDeleteDelivery(delivery.id)}
                                                className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
                                            >
                                                취소
                                            </button>
                                        </>
                                    )}
                                </div>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}