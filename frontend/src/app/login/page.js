"use client";

import { useState } from "react";

export default function ClientPage() {
  const [loginType, setLoginType] = useState("고객");

  return (
    <div className="min-h-screen bg-gray-50 dark:bg-gray-900 text-gray-900 dark:text-white flex flex-col items-center justify-center p-8">
      <div className="flex gap-3 mb-6">
        {["고객","판매자","관리자"].map((type) => (
          <button
            key={type}
            onClick={() => setLoginType(type)}
            className={`px-4 py-2 rounded-lg transition ${
              loginType === type 
                ? "bg-blue-600 text-white" 
                : "bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-300 border border-gray-300 dark:border-gray-600"
            }`}
          >
            {type}
          </button>
        ))}
      </div>

      <form className="w-full max-w-[400px] bg-white dark:bg-gray-800 p-6 rounded-lg shadow-lg">
        <h2 className="text-3xl font-bold text-center mb-6">
          {loginType} 로그인
        </h2>
        <div className="space-y-4">
          <input
            type="text"
            name="username"
            className="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-900 dark:text-white"
            placeholder={`${loginType} 아이디`}
          />
          <input
            type="password"
            name="password"
            className="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-900 dark:text-white"
            placeholder={`${loginType} 비밀번호`}
          />
          <button
            type="submit"
            className="w-full py-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700 transition"
          >
            로그인
          </button>
        </div>
      </form>
    </div>
  );
}