
--MEMBER 테이블 데이터
INSERT INTO MEMBER VALUES (101, 'test1@test.com', '{noop}test1', '테스트1', '010-1234-5678', '서울시 ㅇㅇ구 ㅇㅇ동', 37);
INSERT INTO MEMBER VALUES (102, 'test2@test.com','{noop}test2',  '테스트2', '010-7654-3210', '경기도 xx시 xx구 xx동', 0);
INSERT INTO MEMBER VALUES  (103, 'test3@test.com','{noop}test3',  '테스트3', '010-7777-7777', '인천시 ㅁㅁ구 ㅁㅁ동', 5);

-- BOARD 테이블 데이터
INSERT INTO BOARD VALUES (101, '첫 번째 게시물', 101, '첫 번째 게시물 내용입니다.', 100, 50, '자유게시판', NOW(),NOW());
INSERT INTO BOARD VALUES (102, '두 번째 게시물', 102, '두 번째 게시물 내용입니다.', 120, 60, '스터디모집', NOW(),NOW());
INSERT INTO BOARD VALUES (104, '공지사항 제목1', 101, '공지사항 내용입니다. 첫 번째 공지사항입니다.', 0, 0, '공지사항', NOW(), NOW());
INSERT INTO BOARD VALUES (105, '공지사항 제목2', 102, '공지사항 내용입니다. 두 번째 공지사항입니다.', 0, 0, '공지사항', NOW(), NOW());

-- COMMENT 테이블
INSERT INTO COMMENT VALUES (101, 101,101, '첫 번째 게시물에 대한 댓글입니다.', 31, NOW(),NOW());
INSERT INTO COMMENT VALUES (102, 101, 102, '첫 번째 게시물에 대한 댓글입니다.', 7, NOW(),NOW());
INSERT INTO COMMENT VALUES (103, 102, 103, '두 번째 게시물에 대한 댓글입니다.', 0, NOW(),NOW());
INSERT INTO COMMENT VALUES (104, 102, 102, '두 번째 게시물에 대한 댓글입니다.', 54, NOW(),NOW());


--ITEM테이블 데이터
INSERT INTO ITEM VALUES (101, '로지텍 키보드', 150000, '키보드', '품절', 'WHITE',  'Logitec', 'Keyboard',NOW(),NOW());
INSERT INTO ITEM VALUES (102, '맥북 PRO 13인치', 2500000, 'RAM 16GB', '재고 있음', 'SPACE GRAY',  'APPLE', 'Notebook',NOW(),NOW());
INSERT INTO ITEM VALUES (103, '로지텍 마우스', 100000, '로지텍 마우스', '재고 있음', 'BLACK',  'Logitec', 'Mouse',NOW(),NOW());
INSERT INTO ITEM VALUES (104, '매직 마우스', 100000, '매직 마우스', '재고 있음', 'BLACK',  'APPLE', 'Mouse',NOW(),NOW());

-- CART  테이블 데이터
INSERT INTO CART VALUES (101, 101,NOW(),NOW());
INSERT INTO CART  VALUES (102, 102,NOW(),NOW());

-- REVIEW 테이블 데이터
INSERT INTO REVIEW VALUES (101, 101, 101, '좋은 상품입니다.', 3, 5, 4,NOW(), NOW());
INSERT INTO REVIEW VALUES (102, 101, 102, '키보드 별로에요',14, 3, 4,NOW(), NOW());
INSERT INTO REVIEW VALUES (103, 102, 103, '맥북 사용해보니 좋아요.', 33, 7, 3,NOW(), NOW());
INSERT INTO REVIEW VALUES (104, 103, 101, '좋은 상품입니다.', 3171, 51, 1,NOW(), NOW());
INSERT INTO REVIEW VALUES (105, 103, 103, '마우스 별로에요',33, 3400, 5,NOW(), NOW());

-- CART_ITEM 테이블 데이터
INSERT INTO CART_ITEM VALUES (101, 2, 101, 101,NOW(),NOW());
INSERT INTO CART_ITEM VALUES (102, 1, 102, 102,NOW(),NOW());

-- COMPLAIN 테이블 데이터
INSERT INTO COMPLAIN VALUES (101, 101, 101, '제목','상품에 이상이 있습니다.',NOW(),NOW(),'COMPLAIN_EXIST');
INSERT INTO COMPLAIN VALUES (102, 101, 103, '제목','상품이 파손되었습니다.',NOW(),NOW(),'COMPLAIN_NOT_EXIST');
INSERT INTO COMPLAIN VALUES (103, 102, 102, '제목','이거 언제 입고 되나요?',NOW(),NOW(),'COMPLAIN_EXIST');

-- MESSAGE 테이블 데이터
INSERT INTO MESSAGE VALUES (101, '안녕하세요. 문의드립니다.', NOW(), 101,102);
INSERT INTO MESSAGE VALUES (102, '안녕하세요. 답변드립니다.', NOW(), 102, 101);

--
-- orders 테이블에 주문 정보 삽입
INSERT INTO orders VALUES (101, '202309120001', 50000, 1, 101,NOW(),NOW());
INSERT INTO orders VALUES (102, '202309130002', 50000, 1, 102,NOW(),NOW());
INSERT INTO orders VALUES (103, '202309140001', 1000, 1, 103,NOW(),NOW());


-- pay_info 테이블에 결제 정보 삽입
INSERT INTO pay_info VALUES (1, 'PaymentIdentifier123', 101);

INSERT INTO order_item VALUES(101, 1, 150000, 101, 101);
INSERT INTO order_item VALUES(102, 2, 2500000,102, 102);
INSERT INTO order_item VALUES(103,  2, 5000000, 103, 103);



