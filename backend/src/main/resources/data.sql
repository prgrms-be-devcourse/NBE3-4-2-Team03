-- 카테고리 데이터 삽입
INSERT INTO category (category)
VALUES ('CPU');
INSERT INTO category (category)
VALUES ('GPU');
INSERT INTO category (category)
VALUES ('RAM');

-- 아이템 데이터 삽입
INSERT INTO item (name, img_filename, category_id)
VALUES ('Intel i9-13900K', 'intel_i9_13900k.jpg', 1);
INSERT INTO item (name, img_filename, category_id)
VALUES ('AMD Ryzen 9 7950X', 'amd_ryzen_9_7950x.jpg', 1);
INSERT INTO item (name, img_filename, category_id)
VALUES ('NVIDIA RTX 4090', 'nvidia_rtx_4090.jpg', 2);
INSERT INTO item (name, img_filename, category_id)
VALUES ('Corsair 32GB DDR5', 'corsair_32gb_ddr5.jpg', 3);

-- 판매자 데이터 삽입
INSERT INTO seller (username, password, company_Name, email, verification_question, verification_answer, is_verified)
VALUES ('seller1', 'password123', '컴퓨터나라', 'seller1@computer.com', '가장 좋아하는 음식은?', '피자', true);
INSERT INTO seller (username, password, company_Name, email, verification_question, verification_answer, is_verified)
VALUES ('seller2', 'password456', '디지털프라자', 'seller2@digital.com', '출신 초등학교는?', '행복초등학교', true);

-- 구매자 데이터 삽입
INSERT INTO customer (username, password, customer_Name, email, verification_question, verification_answer)
VALUES ('customer1', 'password123', '맹구', 'customer1@computer.com', '가장 좋아하는 음식은?', '피자');
INSERT INTO customer (username, password, customer_Name, email, verification_question, verification_answer)
VALUES ('customer2', 'password456', '신짱구', 'customer2@digital.com', '출신 초등학교는?', '행복초등학교');

-- 견적 요청 데이터 삽입
INSERT INTO estimate_request (purpose, budget, other_request, create_date, customer_id)
VALUES ('게임용', 2000000, '고사양 게임이 잘 돌아갔으면 좋겠습니다', '2024-03-20 10:30:00', 1);
INSERT INTO estimate_request (purpose, budget, other_request, create_date, customer_id)
VALUES ('사무용', 1000000, '문서 작업이 주 용도입니다', '2024-03-21 11:30:00', 2);
INSERT INTO estimate_request (purpose, budget, other_request, create_date, customer_id)
VALUES ('영상편집용', 3000000, '4K 영상 편집을 주로 합니다', '2024-03-22 14:30:00', 1);

-- 견적서 데이터 삽입
INSERT INTO estimate (estimate_request_id, seller_id, total_price, create_date)
VALUES (1, 1, 2500000, '2024-03-21 10:00:00');
-- 방금 삽입된 estimate의 ID 가져오기
SET @estimate_id = LAST_INSERT_ID();
-- -- 첫 번째 견적서의 구성요소 (게임용 PC)
INSERT INTO estimate_component (item_id, price, estimate_id)
VALUES (1, 850000, @estimate_id); -- Intel i9-13900K
INSERT INTO estimate_component (item_id, price, estimate_id)
VALUES (3, 1500000, @estimate_id); -- NVIDIA RTX 4090
INSERT INTO estimate_component (item_id, price, estimate_id)
VALUES (4, 150000, @estimate_id);  -- Corsair 32GB DDR5

-- 견적서 데이터 삽입
INSERT INTO estimate (estimate_request_id, seller_id, total_price, create_date)
VALUES (2, 2, 800000, '2024-03-22 11:00:00');
-- 방금 삽입된 estimate의 ID 가져오기
SET @estimate_id = LAST_INSERT_ID();
-- 두 번째 견적서의 구성요소 (사무용 PC)
INSERT INTO estimate_component (item_id, price, estimate_id)
VALUES (2, 650000, @estimate_id); -- AMD Ryzen 9 7950X
INSERT INTO estimate_component (item_id, price, estimate_id)
VALUES (4, 150000, @estimate_id); -- Corsair 32GB DDR5

