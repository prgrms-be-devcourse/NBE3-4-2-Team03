import Image from "next/image";

export default function Home() {
  return (
    <div className="grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen p-8 pb-20 gap-16 sm:p-20 font-[family-name:var(--font-geist-sans)]">
      <main className="flex flex-col gap-8 row-start-2 items-center">
        {/* <Image
          className="dark:invert"
          src="/pc-builder-logo.svg"
          alt="PC Builder 로고"
          width={200}
          height={80}
          priority
        /> */}
        
        <h1 className="text-3xl font-bold text-center mb-8">
          나만의 맞춤 PC 견적
        </h1>

        <div className="flex gap-6 items-center flex-col sm:flex-row">
          <a
            className="rounded-full border border-solid border-transparent transition-colors flex items-center justify-center bg-blue-600 text-white gap-2 hover:bg-blue-700 text-sm sm:text-base h-12 px-8 min-w-[160px]"
            href="/login"
          >
            로그인
          </a>
          <a
            className="rounded-full border border-solid border-blue-600 transition-colors flex items-center justify-center hover:bg-blue-50 text-blue-600 hover:text-blue-700 text-sm sm:text-base h-12 px-8 min-w-[160px]"
            href="/signup"
          >
            회원가입
          </a>
        </div>

        <p className="text-gray-600 dark:text-gray-400 text-center mt-4">
          로그인하고 나만의 맞춤 PC 견적을 시작해보세요!
        </p>
      </main>
      
      <footer className="row-start-3 text-sm text-gray-500 dark:text-gray-400">
        © 2025 PC Builder. All rights reserved.
      </footer>
    </div>
  );
}