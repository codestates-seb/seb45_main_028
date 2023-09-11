-- MEMBER 테이블
CREATE TABLE MEMBER
(
    MEMBER_ID  BIGINT primary key AUTO_INCREMENT,
    EMAIL VARCHAR (50) not null  unique,
    PASSWORD VARCHAR (50) not null,
    NAME VARCHAR (30) not null  unique,
    PHONE VARCHAR (30) not null  unique,
    ADDRESS VARCHAR (50) not null,
    REPORT_COUNT BIGINT,
    STATUS VARCHAR (20)
);

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
-- Order 테이블
CREATE TABLE ORDERS (
                    ORDER_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                    MEMBER_ID BIGINT NOT NULL,
                    ORDER_DATE TIMESTAMP,
                    ORDER_STATUS VARCHAR(255),
                    TOTAL_PRICE BIGINT,
                    CREATED_AT TIMESTAMP,
                    LAST_MODIFIED_AT TIMESTAMP,
                    FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER(MEMBER_ID)

);

--OrderDetail 테이블
CREATE TABLE ORDER_ITEM(
                ORDER_ITEM_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                ORDER_ID BIGINT NOT NULL,
                ITEM_ID BIGINT NOT NULL,
                PRICE BIGINT ,
                COUNT BIGINT NOT NULL,
                CREATED_AT TIMESTAMP,
                LAST_MODIFIED_AT TIMESTAMP,
                FOREIGN KEY (ITEM_ID) REFERENCES ITEM(ITEM_ID),
                FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ORDER_ID)

);