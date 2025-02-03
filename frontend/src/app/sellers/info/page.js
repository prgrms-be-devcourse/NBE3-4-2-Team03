'use client';
import { useState } from 'react';

export default function MyPage() {
  const [activeTab, setActiveTab] = useState('profile');
  const [selectedQuote, setSelectedQuote] = useState(null);
  const [selectedQuoteForComment, setSelectedQuoteForComment] = useState(null);
  const [commentText, setCommentText] = useState('');
  const [showConfirmModal, setShowConfirmModal] = useState(false);

  // 임시 데이터 (나중에 API 연동 필요)
  const userInfo = {
    username: "zxc123",
    customer_name: "홍길동",
    email: "hong@example.com",
  };

  const requestedQuotes = [
    {
      id: 1,
      customerId: "user123",
      createDate: "2024-03-19",
      budget: "2,000,000원",
      purpose: "게이밍",
      status: "견적 대기중",
      
    },
    // ... 더 많은 요청 견적들
  ];

  // 임시 데이터에 작성한 견적 데이터 추가
  const writtenQuotes = [
    {
      id: 1,
      customerId: "user123",
      requestId: 1,
      createDate: "2024-03-20",
      totalPrice: "2,100,000원",
      status: "검토중",
      parts: {
        cpu: "Intel Core i7-13700K",
        motherboard: "ASUS ROG STRIX Z690-A",
        memory: "Samsung DDR5-5600 32GB",
        storage: "Samsung 990 PRO 2TB",
        gpu: "NVIDIA GeForce RTX 4070",
        case: "Lian Li O11 Dynamic",
        power: "Corsair RM850x"
      }
    }
  ];

  return (
    <div className="min-h-screen p-8 dark:bg-gray-900">
      <h1 className="text-2xl font-bold mb-8 dark:text-white">판매자 페이지</h1>
      {/* 탭 메뉴 */}
      <div className="flex gap-4 mb-8 border-b dark:border-gray-700">
        <button 
          className={`pb-2 px-4 ${activeTab === 'profile' ? 'border-b-2 border-blue-600 text-blue-600' : 'text-gray-500'}`}
          onClick={() => setActiveTab('profile')}
        >
          회원정보
        </button>
        <button 
          className={`pb-2 px-4 ${activeTab === 'requested' ? 'border-b-2 border-blue-600 text-blue-600' : 'text-gray-500'}`}
          onClick={() => setActiveTab('requested')}
        >
          요청 받은 견적
        </button>
        <button 
          className={`pb-2 px-4 ${activeTab === 'written' ? 'border-b-2 border-blue-600 text-blue-600' : 'text-gray-500'}`}
          onClick={() => setActiveTab('written')}
        >
          작성한 견적
        </button>
      </div>

      {/* 회원정보 탭 */}
      {activeTab === 'profile' && (
        <div className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-sm">
          <div className="grid grid-cols-2 gap-4">
            <div className="text-gray-600 dark:text-gray-400">아이디</div>
            <div className="dark:text-white">{userInfo.username}</div>
            <div className="text-gray-600 dark:text-gray-400">이름</div>
            <div className="dark:text-white">{userInfo.customer_name}</div>
            <div className="text-gray-600 dark:text-gray-400">이메일</div>
            <div className="dark:text-white">{userInfo.email}</div>
          </div>
          <button className="mt-6 text-blue-500 hover:text-blue-400 dark:text-blue-400 dark:hover:text-blue-300">
            회원정보 수정
          </button>
        </div>
      )}

      {/* 요청한 견적 탭 */}
      {activeTab === 'requested' && (
        <div>
          <div className="flex justify-between items-center mb-6">
            <h2 className="text-xl font-semibold dark:text-white">요청 받은 견적 목록</h2>
            
          </div>
          <div className="space-y-8">
            {requestedQuotes.map(quote => (
              <div key={quote.id} className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-sm">
                <div className="flex justify-between items-center mb-4">
                  <div>
                    <span className="text-lg font-semibold dark:text-white">견적 요청 #{quote.id}</span>
                  </div>
                  <button 
                    className="px-4 py-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700 transition-colors"
                    onClick={() => {/* 견적 작성 페이지로 이동 */}}
                  >
                    견적 작성하기
                  </button>
                </div>
                <div className="grid grid-cols-2 gap-2 text-sm mb-6">
                <div className="text-gray-600 dark:text-gray-400">요청자</div>
                <div className="dark:text-white">{quote.customerId}</div>
                  <div className="text-gray-600 dark:text-gray-400">요청일</div>
                  <div className="dark:text-white">{quote.createDate}</div>
                  <div className="text-gray-600 dark:text-gray-400">예산</div>
                  <div className="dark:text-white">{quote.budget}</div>
                  <div className="text-gray-600 dark:text-gray-400">용도</div>
                  <div className="dark:text-white">{quote.purpose}</div>
                </div>  
              </div>
            ))}
          </div>
        </div>
      )}

      {/* 작성한 견적 탭 */}
      {activeTab === 'written' && (
        <div>
          <div className="space-y-8">
            {writtenQuotes.map(quote => (
              <div key={quote.id} className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-sm">
                <div className="flex justify-between items-center mb-4">
                  <div>
                    <span className="text-lg font-semibold dark:text-white">견적 #{quote.id}</span>
                    <span className="ml-3 text-sm text-gray-500 dark:text-gray-400">
                      (요청 #{quote.requestId})
                    </span>
                  </div>
                  <div className="flex gap-2">
                    
                    <button 
                      className="px-3 py-1 rounded-lg border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors"
                      onClick={() => {/* 견적 수정 페이지로 이동 */}}
                    >
                      수정
                    </button>
                    <button 
                      className="px-3 py-1 rounded-lg border border-red-300 dark:border-red-800 text-red-600 dark:text-red-400 hover:bg-red-50 dark:hover:bg-red-900/30 transition-colors"
                      onClick={() => {
                        if (window.confirm('정말로 이 견적을 삭제하시겠습니까?')) {
                          // 견적 삭제 로직 구현
                        }
                      }}
                    >
                      삭제
                    </button>
                  </div>
                </div>
                <div className="grid grid-cols-2 gap-2 text-sm mb-6">
                  <div className="text-gray-600 dark:text-gray-400">요청자</div>
                  <div className="dark:text-white">{quote.customerId}</div>
                  <div className="text-gray-600 dark:text-gray-400">작성일</div>
                  <div className="dark:text-white">{quote.createDate}</div>
                  <div className="text-gray-600 dark:text-gray-400">총 견적금액</div>
                  <div className="dark:text-white">{quote.totalPrice}</div>
                </div>
                <div className="border dark:border-gray-700 rounded-lg p-4">
                  <h4 className="font-medium mb-3 dark:text-white">견적 구성</h4>
                  <div className="grid grid-cols-2 gap-2 text-sm">
                    {Object.entries(quote.parts).map(([part, name]) => (
                      <div key={part} className="col-span-2 grid grid-cols-2">
                        <div className="text-gray-600 dark:text-gray-400">
                          {part === 'cpu' ? 'CPU' :
                           part === 'motherboard' ? '메인보드' :
                           part === 'memory' ? '메모리' :
                           part === 'storage' ? '저장장치' :
                           part === 'gpu' ? '그래픽카드' :
                           part === 'case' ? '케이스' :
                           part === 'power' ? '파워' : part}
                        </div>
                        <div className="dark:text-white">{name}</div>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {selectedQuote && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
          <div className="bg-white dark:bg-gray-800 rounded-lg p-6 max-w-2xl w-full max-h-[90vh] overflow-y-auto">
            <div className="flex justify-between items-center mb-6">
              <h3 className="text-xl font-semibold dark:text-white">견적 상세정보</h3>
              <button 
                onClick={() => setSelectedQuote(null)}
                className="text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-200"
              >
                ✕
              </button>
            </div>
            
            <div className="space-y-6">
              <div className="bg-gray-50 dark:bg-gray-700 p-4 rounded-lg">
                <div className="flex justify-between items-center">
                  <div>
                    <span className="font-medium dark:text-white text-lg">{selectedQuote.seller}</span>
                    <div className="text-sm text-gray-500 dark:text-gray-400 mt-1">견적 받은 날짜: {selectedQuote.date}</div>
                  </div>
                  <span className="px-3 py-1 rounded-full text-sm bg-blue-100 text-blue-600 dark:bg-blue-900 dark:text-blue-300">
                    {selectedQuote.status}
                  </span>
                </div>
              </div>
              
              <div className="border dark:border-gray-700 rounded-lg overflow-hidden">
                <h4 className="font-medium p-4 bg-gray-50 dark:bg-gray-700 dark:text-white border-b dark:border-gray-600">
                  견적 구성 부품
                </h4>
                <div className="divide-y dark:divide-gray-700">
                  {Object.entries(selectedQuote.parts).map(([part, name]) => (
                    <div key={part} className="p-4 hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors">
                      <div className="grid grid-cols-[140px_1fr] gap-4 items-center">
                        <div className="text-gray-600 dark:text-gray-400 font-medium">
                          {part === 'cpu' ? 'CPU' :
                           part === 'motherboard' ? '메인보드' :
                           part === 'memory' ? '메모리' :
                           part === 'storage' ? '저장장치' :
                           part === 'gpu' ? '그래픽카드' :
                           part === 'case' ? '케이스' :
                           part === 'power' ? '파워' : part}
                        </div>
                        <div className="dark:text-white">{name}</div>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
              
              <div className="bg-gray-50 dark:bg-gray-700 p-4 rounded-lg">
                <div className="flex justify-between items-center mb-4">
                  <span className="font-medium dark:text-white">총 견적금액</span>
                  <span className="text-xl font-semibold text-blue-600 dark:text-blue-400">
                    {selectedQuote.totalPrice}
                  </span>
                </div>
                
                <div className="flex justify-end gap-3 mt-4">
                  <button 
                    onClick={() => setSelectedQuote(null)}
                    className="px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors"
                  >
                    닫기
                  </button>
                  <button 
                    className="px-4 py-2 rounded-lg bg-green-600 text-white hover:bg-green-700 transition-colors"
                    onClick={() => {
                      setShowConfirmModal(true);
                    }}
                  >
                    견적 채택하기
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}

      {selectedQuoteForComment && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
          <div className="bg-white dark:bg-gray-800 rounded-lg p-6 max-w-2xl w-full max-h-[90vh] overflow-y-auto">
            <div className="flex justify-between items-center mb-6">
              <h3 className="text-xl font-semibold dark:text-white">문의하기</h3>
              <button 
                onClick={() => setSelectedQuoteForComment(null)}
                className="text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-200"
              >
                ✕
              </button>
            </div>
            
            <div className="space-y-6">
              <div className="bg-gray-50 dark:bg-gray-700 p-4 rounded-lg">
                <span className="font-medium dark:text-white">{selectedQuoteForComment.seller}</span>
                <span className="text-sm text-gray-500 dark:text-gray-400 ml-2">
                  총 견적금액: {selectedQuoteForComment.totalPrice}
                </span>
              </div>

              {/* 댓글 목록 */}
              <div className="space-y-4 max-h-[400px] overflow-y-auto">
                {selectedQuoteForComment.comments?.map((comment) => (
                  <div 
                    key={comment.id} 
                    className={`flex gap-4 ${comment.isCustomer ? 'flex-row-reverse' : ''}`}
                  >
                    <div className="flex-shrink-0">
                      <div className="w-8 h-8 rounded-full bg-gray-200 dark:bg-gray-600 flex items-center justify-center">
                        <span className="text-sm">
                          {comment.author.charAt(0)}
                        </span>
                      </div>
                    </div>
                    <div className={`flex-grow ${comment.isCustomer ? 'text-right' : ''}`}>
                      <div className="flex items-center gap-2 mb-1">
                        <span className="font-medium dark:text-white">{comment.author}</span>
                        <span className="text-sm text-gray-500 dark:text-gray-400">{comment.date}</span>
                      </div>
                      <div className={`inline-block max-w-[80%] rounded-lg px-4 py-2 ${
                        comment.isCustomer 
                          ? 'bg-purple-500 text-white' 
                          : 'bg-gray-100 dark:bg-gray-700 dark:text-white'
                      }`}>
                        {comment.text}
                      </div>
                    </div>
                  </div>
                ))}
              </div>

              {/* 댓글 입력 폼 */}
              <div className="border-t dark:border-gray-700 pt-4">
                <div className="flex gap-2">
                  <input
                    type="text"
                    value={commentText}
                    onChange={(e) => setCommentText(e.target.value)}
                    placeholder="문의사항을 입력하세요..."
                    className="flex-grow px-4 py-2 rounded-lg border dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-900 dark:text-white placeholder-gray-500 dark:placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-purple-500"
                  />
                  <button
                    onClick={() => {
                      if (commentText.trim()) {
                        // 댓글 추가 로직 구현 필요
                        const newComment = {
                          id: Date.now(),
                          author: userInfo.customer_name,
                          text: commentText,
                          date: new Date().toLocaleString(),
                          isCustomer: true
                        };
                        // API 호출 및 상태 업데이트 로직 필요
                        setCommentText('');
                      }
                    }}
                    className="px-6 py-2 rounded-lg bg-purple-600 text-white hover:bg-purple-700 transition-colors disabled:opacity-50"
                    disabled={!commentText.trim()}
                  >
                    전송
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* 채택 확인 모달 */}
      {showConfirmModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
          <div className="bg-white dark:bg-gray-800 rounded-lg p-6 max-w-sm w-full">
            <h3 className="text-lg font-semibold mb-4 dark:text-white">견적 채택</h3>
            <p className="text-gray-600 dark:text-gray-300 mb-6">견적을 채택하시겠습니까?</p>
            <div className="flex justify-end gap-3">
              <button 
                onClick={() => setShowConfirmModal(false)}
                className="px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300"
              >
                아니오
              </button>
              <button 
                onClick={() => {
                  // 채택 로직 구현
                  setShowConfirmModal(false);
                  setSelectedQuote(null);
                }}
                className="px-4 py-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700"
              >
                예
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
} 