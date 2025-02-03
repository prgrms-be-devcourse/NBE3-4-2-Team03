// components/EstimateForm.tsx
"use client"; // Next.js App Router 환경에서 클라이언트 컴포넌트로 선언

import { useState } from "react";
import { createEstimateRequest } from "../service/service";

export default function EstimateForm() {
    const [purpose, setPurpose] = useState("");
    const [budget, setBudget] = useState("0");
    const [otherRequest, setOtherRequest] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await createEstimateRequest(purpose, parseInt(budget), otherRequest);
            alert("견적 요청이 완료되었습니다!");
        } catch (error) {
            alert("요청 실패: " + error);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="p-4 border rounded text-white">
            <div className="mb-4">
                <label className="block mb-2 text-white">목적:</label>
                <input
                    type="text"
                    value={purpose}
                    onChange={(e) => setPurpose(e.target.value)}
                    required
                    className="border p-2 w-full text-black rounded"
                />
            </div>
            <div className="mb-4">
                <label className="block mb-2 text-white">예산:</label>
                <input
                    type="number"
                    value={budget}
                    onFocus={(e) => e.target.value === "0" && setBudget("")}
                    onChange={(e) => setBudget(e.target.value)}
                    required
                    step="10000"
                    className="border p-2 w-full text-black rounded"
                />
            </div>
            <div className="mb-4">
                <label className="block mb-2 text-white">기타 요청 사항:</label>
                <textarea
                    value={otherRequest}
                    onChange={(e) => setOtherRequest(e.target.value)}
                    className="border p-2 w-full text-black rounded"
                    rows={8} // 크기를 두 배로 늘림
                />
            </div>
            <button type="submit" className="mt-2 p-2 bg-blue-500 text-white rounded hover:bg-blue-600">
                견적 요청
            </button>
        </form>
    );
}
