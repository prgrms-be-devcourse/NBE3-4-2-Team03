"use client";

import Link from "next/link";
import { useState } from "react";
import { useRouter } from "next/navigation";

export default function LoginSignupView() {
  const router = useRouter();
  const [formData, setFormData] = useState({
    username: '',
    password: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    await requestLogin("http://localhost:8080/api/auth/login/admin", "/admin/info")
  }

  const requestLogin = async (url, destination) => {
    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData),
        credentials: "include"
      });

      if (response.ok) {
        router.replace(destination);
      } else if (response.status === 400) {
        alert("회원정보가 일치하지 않습니다.");
      }
    } catch (error) {
      alert("error");
    }
  }

  return (
<div className="grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen p-8 pb-20 gap-16 sm:p-20 font-[family-name:var(--font-geist-sans)]">
<main className="flex flex-col gap-8 row-start-2 items-center">
  {
    <div className="flex justify-between mb-6">
      <div>
        <div className ="">
            관리자 로그인
        </div>
        <form onSubmit={handleLogin}>
          <div className="mb-4">
            <label className="block text-sm font-medium text-gray-600">
              아이디
            </label>
            <input
              type="text"
              name="username"
              value={formData.username}
              onChange={handleChange}
              className="w-full mt-2 px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>

          <div className="mb-6">
            <label className="block text-sm font-medium text-gray-600">
              비밀번호
            </label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              className="w-full mt-2 px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>

          <button
            type="submit"
            className="w-full py-2 text-white bg-blue-600 rounded-lg hover:bg-blue-700 focus:outline-none"
          >
            로그인
          </button>
        </form>

        <div className="my-4 border-t border-gray-300"></div>
      </div>
    </div>
    }
      </main>

      <footer className="row-start-3 text-sm text-gray-500 dark:text-gray-400">
        © 2025 PC Builder. All rights reserved.
      </footer>
    </div>
  );
}