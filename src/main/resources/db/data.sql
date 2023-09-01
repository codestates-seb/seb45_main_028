--MEMBER 테이블 데이터
INSERT INTO MEMBER VALUES (101, 'test1@test.com', 'test1', '테스트1', '010-1234-5678', '서울시 ㅇㅇ구 ㅇㅇ동', 37);
INSERT INTO MEMBER VALUES (102, 'test2@test.com','test2',  '테스트2', '010-7654-3210', '경기도 xx시 xx구 xx동', 0);
INSERT INTO MEMBER VALUES  (103, 'test3@test.com','test3',  '테스트3', '010-7777-7777', '인천시 ㅁㅁ구 ㅁㅁ동', 5);

-- BOARD 테이블 데이터
INSERT INTO BOARD VALUES (101, '첫 번째 게시물', 101, '첫 번째 게시물 내용입니다.', NOW(), 100, 50, '자유게시판');
INSERT INTO BOARD VALUES (102, '두 번째 게시물', 102, '두 번째 게시물 내용입니다.', NOW(), 120, 60, '스터디모집');

-- COMMENT 테이블
INSERT INTO COMMENT VALUES (101, 101,101, '첫 번째 게시물에 대한 댓글입니다.', NOW(), 31);
INSERT INTO COMMENT VALUES (102, 101, 102, '첫 번째 게시물에 대한 댓글입니다.', NOW(), 7);
INSERT INTO COMMENT VALUES (103, 102, 103, '두 번째 게시물에 대한 댓글입니다.', NOW(), 0);
INSERT INTO COMMENT VALUES (104, 102, 102, '두 번째 게시물에 대한 댓글입니다.', NOW(), 54);


--ITEM테이블 데이터
INSERT INTO ITEM VALUES (101, '로지텍 키보드', 150000, '키보드', '품절', 'WHITE', 3.8, 'Logitec', 'Keyboard');
INSERT INTO ITEM VALUES (102, '맥북 PRO 13인치', 2500000, 'RAM 16GB', '재고 있음', 'SPACE GRAY', 4.98, 'APPLE', 'Notebook');
INSERT INTO ITEM VALUES (103, '로지텍 마우스', 100000, '로지텍 마우스', '재고 있음', 'BLACK', 4.2, 'Logitec', 'Mouse');
INSERT INTO ITEM VALUES (104, '매직 마우스', 100000, '매직 마우스', '재고 있음', 'BLACK', 3.2, 'APPLE', 'Mouse');

-- CART  테이블 데이터
INSERT INTO CART VALUES (101, 101);
INSERT INTO CART  VALUES (102, 102);

-- REVIEW 테이블 데이터
INSERT INTO REVIEW VALUES (101, 101, 101, '좋은 상품입니다.', NOW(), 3, 5, 4);
INSERT INTO REVIEW VALUES (102, 101, 102, '키보드 별로에요', NOW(), 14, 3, 4);
INSERT INTO REVIEW VALUES (103, 102, 103, '맥북 사용해보니 좋아요.', NOW(), 33, 7, 3);
INSERT INTO REVIEW VALUES (104, 103, 101, '좋은 상품입니다.', NOW(), 3171, 51, 1);
INSERT INTO REVIEW VALUES (105, 103, 103, '마우스 별로에요', NOW(), 33, 3400, 5);

-- CART_ITEM 테이블 데이터
INSERT INTO CART_ITEM VALUES (101, 102, 101, 101);
INSERT INTO CART_ITEM VALUES (102, 101, 102, 102);

-- COMPLAIN 테이블 데이터
INSERT INTO COMPLAIN VALUES (101, 101, 101, '상품에 이상이 있습니다.',NOW(),NOW());
INSERT INTO COMPLAIN VALUES (102, 101, 103, '상품이 파손되었습니다.',NOW(),NOW());
INSERT INTO COMPLAIN VALUES (103, 102, 102, '이거 언제 입고 되나요?',NOW(),NOW());

-- MESSAGE 테이블 데이터
INSERT INTO MESSAGE VALUES (101, '안녕하세요. 문의드립니다.', NOW(), 101,102);
INSERT INTO MESSAGE VALUES (102, '안녕하세요. 답변드립니다.', NOW(), 102, 101);