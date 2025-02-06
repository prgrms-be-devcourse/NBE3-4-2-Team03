"use client";

import Link from "next/link";
import { useState } from "react";

export default function LoginSignupView() {
  const [loginType, setLoginType] = useState("CUSTOMER");

  const handleLoginTypeChange = (type) => {
    setLoginType(type);
  };

  return (
    <div className="flex justify-between mb-6">
      <div>
        <button
          onClick={() => handleLoginTypeChange("CUSTOMER")}
          className={`w-1/2 py-2 rounded-lg focus:outline-none p-4 ${
            loginType === "CUSTOMER"
              ? "bg-blue-600 text-white"
              : "bg-gray-200 text-gray-700"
          }`}
        >
          개인 로그인
        </button>
        <button
          onClick={() => handleLoginTypeChange("SELLER")}
          className={`w-1/2 py-2 rounded-lg focus:outline-none p-4 ${
            loginType === "SELLER"
              ? "bg-green-600 text-white"
              : "bg-gray-200 text-gray-700"
          }`}
        >
          기업 로그인
        </button>

        <form>
          <div className="mb-4">
            <label
              htmlFor="username"
              className="block text-sm font-medium text-gray-600"
            >
              아이디
            </label>
            <input
              type="text"
              id="username"
              name="username"
              className="w-full mt-2 px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>

          <div className="mb-6">
            <label
              htmlFor="password"
              className="block text-sm font-medium text-gray-600"
            >
              비밀번호
            </label>
            <input
              type="password"
              id="password"
              name="password"
              className="w-full mt-2 px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>

          <button
            type="submit"
            className="w-full py-2 text-white bg-blue-600 rounded-lg hover:bg-blue-700 focus:outline-none"
          >
            {loginType === "CUSTOMER" ? "개인 로그인" : "기업 로그인"}
          </button>
        </form>

        <div className="my-4 border-t border-gray-300"></div>

        <div className="text-center">
          {loginType === "CUSTOMER" ? (
            <Link href="/signup/customer">
              <span className="text-blue-600 hover:underline cursor-pointer">
                개인 회원가입
              </span>
            </Link>
          ) : (
            <Link href="/signup/seller">
              <span className="text-blue-600 hover:underline cursor-pointer">
                기업 회원가입
              </span>
            </Link>
          )}
        </div>
      </div>
    </div>
  );
}