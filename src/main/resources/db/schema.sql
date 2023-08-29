-- MEMBER 테이블
CREATE Table MEMBER
(
    MEMBER_ID LONG primary key AUTO_INCREMENT,
    EMAIL VARCHAR (50) not null  unique,
    PASSWORD VARCHAR (50) not null,
    NAME VARCHAR (30) not null  unique,
    PHONE VARCHAR (30) not null  unique,
    ADDRESS VARCHAR (50) not null,
    REPORT_COUNT LONG
);
-- BOARD 테이블
CREATE TABLE BOARD (
                      BOARD_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                      TITLE VARCHAR(100) NOT NULL,
                      MEMBER_ID BIGINT NOT NULL,
                      CONTENT VARCHAR(1000),
                      CREATED_AT TIMESTAMP NOT NULL,
                      VIEW_COUNT BIGINT,
                      LIKE_COUNT BIGINT,
                      BOARD_CATEGORY VARCHAR(100) NOT NULL,
                      FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER(MEMBER_ID)
);

-- COMMENT 테이블
CREATE TABLE COMMENT (
                         COMMENT_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                         BOARD_ID BIGINT NOT NULL,
                         MEMBER_ID BIGINT NOT NULL,
                         CONTENT VARCHAR(1000),
                         CREATED_AT TIMESTAMP,
                         LIKE_COUNT BIGINT,
                         FOREIGN KEY (BOARD_ID) REFERENCES BOARD(BOARD_ID),
                         FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER(MEMBER_ID)
);

-- ITEM 테이블
CREATE TABLE ITEM (
                      ITEM_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                      ITEM_NAME VARCHAR(100),
                      ITEM_PRICE BIGINT,
                      ITEM_DETAIL VARCHAR(100),
                      ITEM_STATUS VARCHAR(100),
                      ITEM_COLOR VARCHAR(100),
                      ITEM_SCORE DOUBLE,
                      ITEM_BRAND VARCHAR(100),
                      ITEM_CATEGORY VARCHAR(100)
);

-- CART 테이블
CREATE TABLE CART (
                      CART_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                      MEMBER_ID BIGINT NOT NULL,
                      FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER(MEMBER_ID)
);

-- REVIEW 테이블
CREATE TABLE REVIEW (
                        REVIEW_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                        ITEM_ID BIGINT NOT NULL,
                        MEMBER_ID BIGINT NOT NULL,
                        CONTENT VARCHAR(1000),
                        CREATED_AT TIMESTAMP,
                        LIKE_COUNT BIGINT,
                        UNLIKE_COUNT BIGINT,
                        FOREIGN KEY (ITEM_ID) REFERENCES ITEM(ITEM_ID),
                        FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER(MEMBER_ID)
);

-- CART_ITEM 테이블
CREATE TABLE CART_ITEM (
                           CART_ITEM_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                           COUNT BIGINT NOT NULL,
                           CART_ID BIGINT NOT NULL,
                           ITEM_ID BIGINT NOT NULL,
                           FOREIGN KEY (CART_ID) REFERENCES CART(CART_ID),
                           FOREIGN KEY (ITEM_ID) REFERENCES ITEM(ITEM_ID)
);

-- COMPLAIN 테이블
CREATE TABLE COMPLAIN (
                          COMPLAIN_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                          MEMBER_ID BIGINT NOT NULL,
                          ITEM_ID BIGINT NOT NULL,
                          CONTENT VARCHAR(1000),
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
