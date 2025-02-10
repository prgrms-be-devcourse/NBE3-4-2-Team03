// 쿠키에서 값을 가져오는 헬퍼 함수
function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
    return '';
}

// 인증 헤더를 생성하는 헬퍼 함수
function getAuthorizationHeader() {
    const apiKey = getCookie('apiKey');
    const accessToken = getCookie('accessToken');
    const userType = getCookie('userType');
    return `Bearer ${apiKey} ${accessToken} ${userType}`;
}

// 배송 목록 조회
export async function fetchDeliveryList() {
    try {
        const response = await fetch('http://localhost:8080/delivery', {
            method: 'GET',
            headers: {
                'Authorization': getAuthorizationHeader(),
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            credentials: 'include'
        });

        if (!response.ok) {
            const errorData = await response.text();
            try {
                const parsedError = JSON.parse(errorData);
                throw new Error(parsedError.message);
            } catch (e) {
                // JSON 파싱 실패 시 원본 에러 메시지 사용
                throw new Error(errorData);
            }
        }

        return response.json();
    } catch (error) {
        console.error('배송 목록 조회 오류:', error);
        throw error; // 에러를 상위로 전파
    }
}

// 배송 정보 수정
export async function updateDelivery(deliveryId, address) {
    try {
        const response = await fetch(`http://localhost:8080/delivery/${deliveryId}`, {
            method: 'PUT',
            headers: {
                'Authorization': getAuthorizationHeader(),
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            credentials: 'include',
            body: JSON.stringify({ address })
        });

        if (!response.ok) {
            const errorData = await response.text();
            try {
                const parsedError = JSON.parse(errorData);
                throw new Error(parsedError.message);
            } catch (e) {
                throw new Error(errorData);
            }
        }

        return response.text();
    } catch (error) {
        console.error('배송 정보 수정 오류:', error);
        throw error;
    }
}

// 배송 삭제
export async function deleteDelivery(deliveryId) {
    try {
        const response = await fetch(`http://localhost:8080/delivery/${deliveryId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': getAuthorizationHeader(),
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            credentials: 'include'
        });

        if (!response.ok) {
            const errorData = await response.text();
            try {
                const parsedError = JSON.parse(errorData);
                throw new Error(parsedError.message);
            } catch (e) {
                throw new Error(errorData);
            }
        }

        return response.text();
    } catch (error) {
        console.error('배송 삭제 오류:', error);
        throw error;
    }
}