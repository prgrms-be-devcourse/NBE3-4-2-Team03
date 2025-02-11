'use client';
import { useState, useEffect } from 'react';
import Link from 'next/link';
import { useRouter } from "next/navigation";

export default function MyPage() {
  const router = useRouter();
  const [activeTab, setActiveTab] = useState('profile');
  const [selectedQuote, setSelectedQuote] = useState(null);
  const [selectedQuoteForComment, setSelectedQuoteForComment] = useState(null);
  const [commentText, setCommentText] = useState('');
  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [requestedQuotes, setRequestedQuotes] = useState([]);
  const [writtenQuotes, setWrittenQuotes] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [deliveryInfo, setDeliveryInfo] = useState({});
  const [sellerInfo, setSellerInfo] = useState({
    id: '',
    username: '',
    companyName: '',
    email: ''
  });

  useEffect(() => {
    getSellerInfo()
  }, [])

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        if (activeTab === 'requested') {
          const response = await fetch('http://localhost:8080/estimate/request', {
            credentials: 'include'
          });
          if (!response.ok) throw new Error('견적 데이터를 불러오는데 실패했습니다');
          const data = await response.json();
          console.log('요청받은 견적 데이터:', data);
          setRequestedQuotes(data);
        } else if (activeTab === 'written') {
          const response = await fetch(`http://localhost:8080/api/estimate/seller`, {
            credentials: 'include'
          });
          if (!response.ok) throw new Error('작성한 견적 데이터를 불러오는데 실패했습니다');
          const data = await response.json();
          console.log('작성한 견적 데이터:', data);
          setWrittenQuotes(data);
        }
      } catch (err) {
        setError(err.message);
      } finally {
        setIsLoading(false);
      }
    };

    if (activeTab === 'requested' || activeTab === 'written') {
      fetchData();
    }
  }, [activeTab]);

  // 배송 정보를 가져오는 useEffect 추가
  useEffect(() => {
    const fetchDeliveryInfo = async () => {
      try {
        const response = await fetch('http://localhost:8080/delivery', {
          credentials: 'include'
        });
        if (response.ok) {
          const data = await response.json();
          // deliveryId를 key로 하는 객체로 변환
          const deliveryMap = {};
          data.forEach(delivery => {
            deliveryMap[delivery.estimateId] = delivery;
          });
          setDeliveryInfo(deliveryMap);
        }
      } catch (error) {
        console.error('배송 정보 조회 실패:', error);
      }
    };

    if (activeTab === 'written') {
      fetchDeliveryInfo();
    }
  }, [activeTab]);

  const getSellerInfo = () => {
    fetch("http://localhost:8080/seller", {
      method: "GET",
      credentials: "include",
    })
        .then((response) => response.json())
        .then((data) => {
          setSellerInfo(data);
        });
  }

  const handleLogout = async (e) => {
    e.preventDefault();
    const response = await fetch("http://localhost:8080/api/auth/logout", {
      method: 'POST',
      credentials: 'include'
    });

    if (response.ok) {
      router.replace("/");
    }
  }

  const getStatusStyle = (status) => {
    switch(status) {
      case 'ADOPT':
      case 'Adopt':
        return {
          bgColor: 'bg-green-100 dark:bg-green-900',
          textColor: 'text-green-800 dark:text-green-200',
          text: '채택됨'
        };
      case 'WAIT':
      case 'Wait':
        return {
          bgColor: 'bg-blue-100 dark:bg-blue-900',
          textColor: 'text-blue-800 dark:text-blue-200',
          text: '대기중'
        };
      default:
        return {
          bgColor: 'bg-gray-100 dark:bg-gray-700',
          textColor: 'text-gray-800 dark:text-gray-200',
          text: '상태 없음'
        };
    }
  };

  return (
      <div className="min-h-screen p-8 dark:bg-gray-900">
        <h1 className="text-2xl font-bold mb-8 dark:text-white">판매자 페이지</h1>

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
                <div className="dark:text-white">{sellerInfo.username}</div>
                <div className="text-gray-600 dark:text-gray-400">이름</div>
                <div className="dark:text-white">{sellerInfo.companyName}</div>
                <div className="text-gray-600 dark:text-gray-400">이메일</div>
                <div className="dark:text-white">{sellerInfo.email}</div>
              </div>
              <div className="mt-6">
                <button onClick={handleLogout} className="text-blue-500 hover:text-blue-400">
                  로그아웃
                </button>
              </div>
            </div>
        )}

        {/* 요청받은 견적 탭 */}
        {activeTab === 'requested' && (
            <div>
              {isLoading ? (
                  <div className="text-center py-8 dark:text-white">로딩중...</div>
              ) : error ? (
                  <div className="text-center py-8 text-red-500">{error}</div>
              ) : (
                  <div className="space-y-8">
                    {requestedQuotes.length === 0 ? (
                        <div className="text-center py-8 dark:text-white">요청받은 견적이 없습니다.</div>
                    ) : (
                        requestedQuotes.map(quote => {
                          const statusStyle = getStatusStyle(quote.status);
                          return (
                              <div key={quote.id} className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-sm">
                                <div className="flex justify-between items-center mb-4">
                                  <div className="flex items-center gap-3">
                                    <span className="text-lg font-semibold dark:text-white">견적 요청 #{quote.id}</span>
                                    <span className={`px-2 py-1 rounded-full text-sm ${statusStyle.bgColor} ${statusStyle.textColor}`}>
                            {statusStyle.text}
                          </span>
                                  </div>
                                  {(quote.status === 'WAIT' || quote.status === 'Wait') && (
                                      <Link
                                          href={{
                                            pathname: '/estimate/create',
                                            query: {
                                              requestId: quote.id,
                                              customerName: quote.customerId,
                                              budget: quote.budget,
                                              purpose: quote.purpose,
                                              createDate: quote.createDate,
                                            }
                                          }}
                                          className="px-4 py-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700 transition-colors"
                                      >
                                        견적 작성하기
                                      </Link>
                                  )}
                                </div>
                                <div className="grid grid-cols-2 gap-2 text-sm">
                                  <div className="text-gray-600 dark:text-gray-400">요청자</div>
                                  <div className="dark:text-white">{quote.customerId}</div>
                                  <div className="text-gray-600 dark:text-gray-400">요청일</div>
                                  <div className="dark:text-white">{new Date(quote.createDate).toLocaleDateString()}</div>
                                  <div className="text-gray-600 dark:text-gray-400">예산</div>
                                  <div className="dark:text-white">{quote.budget.toLocaleString()}원</div>
                                  <div className="text-gray-600 dark:text-gray-400">용도</div>
                                  <div className="dark:text-white">{quote.purpose}</div>
                                  <div className="text-gray-600 dark:text-gray-400">기타 요청사항</div>
                                  <div className="dark:text-white">{quote.otherRequest || '-'}</div>
                                </div>
                              </div>
                          );
                        })
                    )}
                  </div>
              )}
            </div>
        )}

        {/* 작성한 견적 탭 */}
        {activeTab === 'written' && (
            <div>
              {isLoading ? (
                  <div className="text-center py-8 dark:text-white">로딩중...</div>
              ) : error ? (
                  <div className="text-center py-8 text-red-500">{error}</div>
              ) : (
                  <div className="space-y-8">
                    {writtenQuotes.length === 0 ? (
                        <div className="text-center py-8 dark:text-white">작성한 견적이 없습니다.</div>
                    ) : (
                        writtenQuotes.map(quote => {
                          console.log('개별 견적 데이터:', quote);
                          const requestStatus = quote.status || 'WAIT';
                          const statusStyle = getStatusStyle(requestStatus);
                          const delivery = deliveryInfo[quote.id];

                          return (
                              <div key={quote.id} className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-sm">
                                <div className="flex justify-between items-center mb-4">
                                  <div className="flex items-center gap-3">
                                    <span className="text-lg font-semibold dark:text-white">견적 #{quote.id}</span>
                                    <span className={`px-2 py-1 rounded-full text-sm ${statusStyle.bgColor} ${statusStyle.textColor}`}>
                            {statusStyle.text}
                          </span>
                                  </div>
                                </div>
                                <div className="grid grid-cols-2 gap-2 text-sm">
                                  <div className="text-gray-600 dark:text-gray-400">요청자</div>
                                  <div className="dark:text-white">{quote.customer}</div>
                                  <div className="text-gray-600 dark:text-gray-400">요청일</div>
                                  <div className="dark:text-white">{new Date(quote.date).toLocaleDateString()}</div>
                                  <div className="text-gray-600 dark:text-gray-400">총 견적 금액</div>
                                  <div className="dark:text-white">{quote.totalPrice.toLocaleString()}원</div>
                                  <div className="text-gray-600 dark:text-gray-400">견적 상세</div>
                                  <div className="dark:text-white whitespace-pre-wrap">{quote.details || '-'}</div>
                                  {delivery && (
                                      <>
                                        <div className="text-gray-600 dark:text-gray-400">배송 주소</div>
                                        <div className="dark:text-white">{delivery.address || '-'}</div>
                                        <div className="text-gray-600 dark:text-gray-400">배송 상태</div>
                                        <div className="dark:text-white">
                              <span className="px-2 py-1 bg-blue-100 text-blue-600 rounded-full text-sm">
                                {delivery.status || '배송 준비중'}
                              </span>
                                        </div>
                                      </>
                                  )}
                                </div>
                              </div>
                          );
                        })
                    )}
                  </div>
              )}
            </div>
        )}
      </div>
  );
}