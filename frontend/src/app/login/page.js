"use client";

import { useState } from "react";

export default function ClientPage() {
  const [loginType, setLoginType] = useState("고객");

  return (
    <div className="flex flex-col items-center justify-center min-h-screen">
      <div className="flex gap-3 mb-6">
        {["고객","판매자","관리자"].map((type) => (
          <button
            key={type}
            onClick={() => setLoginType(type)}
            className={`px-2 py-1 rounded-md border border-gray-300 transition ${
              loginType === type ? "bg-blue-600 text-white" : "bg-gray text-white"
            }`}
          >
            {type}
          </button>
        ))}
      </div>

      <form className="w-full max-w-[400px]">
        <div className="p-5  shadow-lg rounded-lg">
          <h2 className="text-3xl font-bold text-center mb-1">
            {loginType} 로그인
          </h2>
          <div className="p-6  rounded-lg">
            <input
              type="text"
              name="username"
              id="username"
              className="p-2 h-[50px] rounded-full w-full border border-gray-100 text-black placeholder-gray-400"
              placeholder={`${loginType} 아이디`}
            />

            <input
              type="password"
              name="password"
              id="password"
              className="p-2 mt-3 h-[50px] rounded-full w-full border border-gray-100 text-black placeholder-gray-400"
              placeholder={`${loginType} 비밀번호`}
            />
            <button
              type="submit"
              className="mt-6 w-full h-12 rounded-full bg-blue-600 text-white hover:bg-blue-700 transition"
            >
              로그인
            </button>
          </div>
        </div>
      </form>
    </div>
  );
}