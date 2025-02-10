'use client';
import { useState, useEffect } from 'react';
import Link from 'next/link';
import { useRouter } from "next/navigation";

/**
 *
 * @param {{id:number}} quote
 * @param {({id:number})=>{}} onConfirm
 * @param {({id:number})=>{}} onComment
 * @param {({id:number})=>{}} onSelectQuote
 * @returns
 */
const QuoteComponent = ({quote,onConfirm,onComment,onSelectQuote})=>{
  const [selected,setSelected] = useState(false)
  const [receivedQuotes,setReceivedQuotes] = useState([])
  // 받은 견적 목록 조회
  useEffect(() => {
    if (!selected)return;
    if (receivedQuotes.length>0)return;
    const fetchReceivedQuotes = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/estimate/${quote.id}`);
        if (!response.ok) {
          throw new Error('받은 견적 데이터를 가져오는데 실패했습니다');
        }
        const data = await response.json();
        console.log(data)
        setReceivedQuotes(data);
      } catch (error) {
        console.error('받은 견적 데이터 로딩 오류:', error);
      }
    };
    fetchReceivedQuotes();

  }, [selected]);

  return (
      <div key={quote.id}
           className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-sm"
      >
        <div className="flex justify-between items-center mb-4">
        <span className="text-lg font-semibold dark:text-white">
          견적 요청 #{quote.id}
        </span>
          <button
              onClick={()=>setSelected(p=>!p)}
              className="px-3 py-1 border border-gray-300 dark:border-gray-600 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors"
          >
            {selected?"닫기":"열기"}
          </button>
        </div>
        <div className="grid grid-cols-2 gap-2 text-sm mb-6">
          <div className="text-gray-600 dark:text-gray-400">요청일</div>
          <div className="dark:text-white">{quote.createDate}</div>
          <div className="text-gray-600 dark:text-gray-400">예산</div>
          <div className="dark:text-white">{quote.budget}</div>
          <div className="text-gray-600 dark:text-gray-400">용도</div>
          <div className="dark:text-white">{quote.purpose}</div>
        </div>
        {/* 받은 견적 목록 */}
        {selected && receivedQuotes.length > 0 && (
            <div className="mt-6 border-t dark:border-gray-700 pt-4">
              <h3 className="text-md font-semibold mb-4 dark:text-white">받은 견적 ({receivedQuotes.length})</h3>
              <div className="space-y-4">
                {receivedQuotes.map(receivedQuote => (
                    <div key={receivedQuote.id} className="bg-gray-50 dark:bg-gray-700 p-4 rounded-lg">
                      <div className="flex justify-between items-center mb-2">
                        <span className="font-medium dark:text-white">{receivedQuote.seller}</span>
                      </div>
                      <div className="grid grid-cols-2 gap-2 text-sm">
                        <div className="text-gray-600 dark:text-gray-400">받은날짜</div>
                        <div className="dark:text-white">{receivedQuote.date}</div>
                        <div className="text-gray-600 dark:text-gray-400">견적금액</div>
                        <div className="dark:text-white">{receivedQuote.totalPrice}</div>
                      </div>
                      <div className="mt-3 flex gap-2">
                        <button
                            className="text-sm text-blue-500 hover:text-blue-400"
                            onClick={() => onSelectQuote(receivedQuote)}
                        >
                          상세보기
                        </button>
                        <button
                            className="text-sm text-green-600 hover:text-green-500"
                            onClick={()=>onConfirm(receivedQuote)}
                        >
                          채택하기
                        </button>
                        <button
                            className="text-sm text-purple-600 hover:text-purple-500"
                            onClick={()=>onComment(receivedQuote)}
                        >
                          문의하기
                        </button>
                      </div>
                    </div>
                ))}
              </div>
            </div>
        )}
      </div>
  )
}

export default function MyPage() {
  const router = useRouter();
  const [activeTab, setActiveTab] = useState('profile');
  const [selectedQuote, setSelectedQuote] = useState(null);
  const [selectedQuoteForComment, setSelectedQuoteForComment] = useState(null);
  const [commentText, setCommentText] = useState('');
  const [confirmQuote, setConfirmQuote] = useState(null);
  const [requestedQuotes, setRequestedQuotes] = useState([]);
  const [deliveryAddress, setDeliveryAddress] = useState('');
  const [customerInfo, setCustomerInfo] = useState({
    id: '',
    username: '',
    customerName: '',
    email: ''
  });

  /**
   *
   * @param {{id:number}} quote
   */
  const onSelcectQuote = (quote)=>{
    setSelectedQuote(quote);
  }
  /**
   *
   * @param {{id:number}} quote
   */
  const onConfirm = (quote)=>{
    setConfirmQuote(quote);
  }
  /**
   *
   * @param {{id:number}} quoteId
   */
  const onComment =(quote)=>{
    setSelectedQuoteForComment(quote)
  }

  useEffect(() => {
    getCustomerInfo();
  }, [])

  // 견적 요청 목록 조회
  useEffect(() => {
    const fetchQuotes = async () => {
      try {
        const response = await fetch('http://localhost:8080/estimate/request', {
          credentials: 'include'
        });
        if (!response.ok) {
          throw new Error('견적 데이터를 가져오는데 실패했습니다');
        }
        const data = await response.json();
        setRequestedQuotes(data);
      } catch (error) {
        console.error('견적 데이터 로딩 오류:', error);
      }
    };

    if (activeTab === 'requested') {
      fetchQuotes();
    }
  }, [activeTab]);

  const getCustomerInfo = () => {
    fetch("http://localhost:8080/customer", {
      method: "GET",
      credentials: "include",
    })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          setCustomerInfo(data);
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

  return (
      <div className="min-h-screen p-8 dark:bg-gray-900">
        <h1 className="text-2xl font-bold mb-8 dark:text-white">구매자 페이지</h1>

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
          요청한 견적
        </button>
          {/* 배송 관리 버튼 추가 */}
          <Link href="/delivery">
            <button
                className="pb-2 px-4 text-gray-500 hover:text-blue-600"
            >
              배송 관리
            </button>
          </Link>
        </div>

        {/* 회원정보 탭 */}
        {activeTab === 'profile' && (
            <div className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-sm">
              <div className="grid grid-cols-2 gap-4">
                <div className="text-gray-600 dark:text-gray-400">아이디</div>
                <div className="dark:text-white">{customerInfo.username}</div>
                <div className="text-gray-600 dark:text-gray-400">이름</div>
                <div className="dark:text-white">{customerInfo.customerName}</div>
                <div className="text-gray-600 dark:text-gray-400">이메일</div>
                <div className="dark:text-white">{customerInfo.email}</div>
              </div>
              <button className="mt-6 text-blue-500 hover:text-blue-400 dark:text-blue-400 dark:hover:text-blue-300">
                회원정보 수정
              </button>
              <div>
                <button onClick={handleLogout}
                        className="mt-6 text-blue-500 hover:text-blue-400 dark:text-blue-400 dark:hover:text-blue-300">
                  로그아웃
                </button>
              </div>
            </div>
        )}

        {/* 요청한 견적 탭 */}
        {activeTab === 'requested' && (
            <div>
              <div className="space-y-8">
                {requestedQuotes.map(quote => (
                    <QuoteComponent
                        key={quote.id}
                        quote={quote}
                        onConfirm={onConfirm}
                        onComment={onComment}
                        onSelectQuote={onSelcectQuote}
                    />
                ))}
              </div>
              <Link href="/estimateRequest">
                <button className="mt-4 px-4 py-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700 transition-colors">
                  견적 요청하기
                </button>
              </Link>
            </div>
        )}

        {/* 견적 상세 모달 */}
        {selectedQuote && (
            <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
              <div className="bg-white dark:bg-gray-800 rounded-lg p-6 max-w-2xl w-full max-h-[90vh] overflow-y-auto">
                {/* ... 견적 상세 내용 ... */}
              </div>
            </div>
        )}

        {/* 문의하기 모달 */}
        {selectedQuoteForComment && (
            <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
              <div className="bg-white dark:bg-gray-800 rounded-lg p-6 max-w-2xl w-full max-h-[90vh] overflow-y-auto">
                {/* ... 문의하기 내용 ... */}
              </div>
            </div>
        )}

        {/* 채택 확인 모달 */}
        {Boolean(confirmQuote) && (
            <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
              <div className="bg-white dark:bg-gray-800 rounded-lg p-6 max-w-sm w-full">
                <h3 className="text-lg font-semibold mb-4 dark:text-white">견적 채택</h3>
                <div className="mb-4">
                  <label className="block text-gray-700 dark:text-gray-300 mb-2">배송 주소</label>
                  <input
                      type="text"
                      className="w-full px-3 py-2 border rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white"
                      placeholder="배송받으실 주소를 입력해주세요"
                      value={deliveryAddress}
                      onChange={(e) => setDeliveryAddress(e.target.value)}
                  />
                </div>
                <p className="text-gray-600 dark:text-gray-300 mb-6">입력하신 주소로 배송됩니다. 계속하시겠습니까?</p>
                <div className="flex justify-end gap-3">
                  <button
                      onClick={() => {
                        setConfirmQuote(null);
                        setDeliveryAddress('');
                      }}
                      className="px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300"
                  >
                    아니오
                  </button>
                  <button
                      onClick={async () => {
                        try {
                          const response = await fetch(`http://localhost:8080/delivery?id=${confirmQuote.id}`, {
                            method: 'POST',
                            headers: {
                              'Content-Type': 'application/json',
                              'Accept': 'application/json'
                            },
                            credentials: 'include',
                            body: JSON.stringify({
                              address: deliveryAddress
                            })
                          });

                          if (!response.ok) {
                            const errorData = await response.text();
                            throw new Error(errorData || '배송 요청 실패');
                          }

                          // 성공 처리
                          alert('견적이 채택되었습니다.');

                          // 견적 목록 새로고침
                          const updatedResponse = await fetch('http://localhost:8080/estimate/request', {
                            credentials: 'include'
                          });
                          if (updatedResponse.ok) {
                            const updatedData = await updatedResponse.json();
                            setRequestedQuotes(updatedData);
                          }

                          setConfirmQuote(null);
                          setDeliveryAddress('');

                          // 배송 관리 페이지로 이동
                          router.push('/delivery');
                        } catch (error) {
                          console.error('배송 요청 오류:', error);
                          alert('견적 채택 중 오류가 발생했습니다.');
                        }
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