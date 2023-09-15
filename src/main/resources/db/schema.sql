-- MEMBER 테이블
CREATE TABLE MEMBER
(
    MEMBER_ID  BIGINT primary key AUTO_INCREMENT,
    EMAIL VARCHAR (50) not null  unique,
    PASSWORD TEXT not null,
    NAME VARCHAR (30) not null  unique,
    PHONE VARCHAR (1000) not null  unique,
    ADDRESS VARCHAR (1000) not null,
    REPORT_COUNT BIGINT);

CREATE TABLE MEMBER_ROLES (
                              id  BIGINT AUTO_INCREMENT PRIMARY KEY,
                              member_member_id BIGINT,
                              roles VARCHAR(255),
                              FOREIGN KEY (member_member_id) REFERENCES MEMBER(member_id)
);

-- BOARD 테이블
CREATE TABLE BOARD (

                       BOARD_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                       TITLE VARCHAR(100) NOT NULL,
                       MEMBER_ID BIGINT NOT NULL,
                       CONTENT VARCHAR(1000),
                       VIEW_COUNT BIGINT,
                       LIKE_COUNT BIGINT,
                       BOARD_CATEGORY VARCHAR(100) NOT NULL,
                       CREATED_AT TIMESTAMP,
                       LAST_MODIFIED_AT TIMESTAMP,
                       FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER(MEMBER_ID)
);

-- COMMENT 테이블
CREATE TABLE COMMENT (
                         COMMENT_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                         BOARD_ID BIGINT NOT NULL,
                         MEMBER_ID BIGINT NOT NULL,
                         CONTENT VARCHAR(1000),
                         LIKE_COUNT BIGINT,
                         CREATED_AT TIMESTAMP,
                         LAST_MODIFIED_AT TIMESTAMP,
                         FOREIGN KEY (BOARD_ID) REFERENCES BOARD(BOARD_ID),
                         FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER(MEMBER_ID)
);

-- ITEM 테이블
CREATE TABLE ITEM (
                      ITEM_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                      NAME VARCHAR(100),
                      PRICE BIGINT,
                      DETAIL VARCHAR(100),
                      STATUS VARCHAR(100),
                      COLOR VARCHAR(100),
                      BRAND VARCHAR(100),
                      CATEGORY VARCHAR(100),
                      CREATED_AT TIMESTAMP,
                      LAST_MODIFIED_AT TIMESTAMP
);

-- CART 테이블
CREATE TABLE CART (
                      CART_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                      MEMBER_ID BIGINT NOT NULL,
                      CREATED_AT TIMESTAMP,
                      LAST_MODIFIED_AT TIMESTAMP,
                      FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER(MEMBER_ID)
);

-- REVIEW 테이블
CREATE TABLE REVIEW (
                        REVIEW_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                        ITEM_ID BIGINT NOT NULL,
                        MEMBER_ID BIGINT NOT NULL,
                        CONTENT VARCHAR(1000),
                        LIKE_COUNT  BIGINT,
                        UNLIKE_COUNT  BIGINT,
                        SCORE BIGINT,
                        CREATED_AT TIMESTAMP,
                        LAST_MODIFIED_AT TIMESTAMP,
                        FOREIGN KEY (ITEM_ID) REFERENCES ITEM(ITEM_ID),
                        FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER(MEMBER_ID)
);

-- CART_ITEM 테이블
CREATE TABLE CART_ITEM (
                           CART_ITEM_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                           COUNT BIGINT NOT NULL,
                           CART_ID BIGINT NOT NULL,
                           ITEM_ID BIGINT NOT NULL,
                           CREATED_AT TIMESTAMP,
                           LAST_MODIFIED_AT TIMESTAMP,
                           FOREIGN KEY (CART_ID) REFERENCES CART(CART_ID),
                           FOREIGN KEY (ITEM_ID) REFERENCES ITEM(ITEM_ID)
);

-- COMPLAIN 테이블
CREATE TABLE COMPLAIN (
                          COMPLAIN_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                          MEMBER_ID BIGINT NOT NULL,
                          ITEM_ID BIGINT NOT NULL,
                          title VARCHAR(20),
                          CONTENT VARCHAR(1000),
                          CREATED_AT TIMESTAMP,
                          LAST_MODIFIED_AT TIMESTAMP,
                          COMPLAIN_STATUS VARCHAR(255),
                          FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER(MEMBER_ID),
                          FOREIGN KEY (ITEM_ID) REFERENCES ITEM(ITEM_ID)
);

-- MESSAGE 테이블
CREATE TABLE MESSAGE (
                         MESSAGE_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                         CONTENT VARCHAR(1000),
                         CREATED_AT TIMESTAMP,
                         SENDER_MEMBER_ID BIGINT NOT NULL,
                         RESPONSE_MEMBER_ID BIGINT NOT NULL,
                         FOREIGN KEY (SENDER_MEMBER_ID) REFERENCES MEMBER(MEMBER_ID),
                         FOREIGN KEY (RESPONSE_MEMBER_ID) REFERENCES MEMBER(MEMBER_ID)
);
-- orders 테이블 생성
CREATE TABLE orders (
                        order_id INT AUTO_INCREMENT PRIMARY KEY,
                        order_number VARCHAR(255) NOT NULL UNIQUE,
                        total_price INT,
                        status INT,
                        member_id INT,
                        CREATED_AT TIMESTAMP,
                        LAST_MODIFIED_AT TIMESTAMP,
                        FOREIGN KEY (member_id) REFERENCES member (member_id)
);

-- order_item 테이블 생성
CREATE TABLE order_item (
                            order_item_id INT AUTO_INCREMENT PRIMARY KEY,
                            quantity LONG,
                            price LONG,
                            item_id INT,
                            order_id INT,
                            FOREIGN KEY (item_id) REFERENCES item (item_id),
                            FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

-- pay_info 테이블 생성
CREATE TABLE pay_info (
                          payment_Id INT AUTO_INCREMENT PRIMARY KEY,
                          imp_Uid varchar(250),
                          order_id INT,
                          FOREIGN KEY (order_id) REFERENCES orders (order_id)
);
