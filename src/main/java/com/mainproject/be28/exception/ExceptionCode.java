package com.mainproject.be28.exception;

import lombok.Getter;

public enum ExceptionCode {
    NOT_FOUND(404, "not found" ),
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    MEMBER_NOT_AUTHORIZED(401, "Member not authorized"),
    POST_NOT_FOUND(404, "POST not found"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    ITEM_NOT_FOUND(404, "ITEM not found"),
    ITEM_EXIST(409, "동일한 상품명이 존재합니다."),
    CART_NOT_FOUND(404, "CART NOT FOUND"),
    BOARD_NOT_FOUND(404, "BOARD not found"),
    Complain_NOT_FOUND(404,"COMPLAIN not found"),
    Review_NOT_FOUND(404,"REVIEW not found" ),
    IMAGE_NOT_FOUND(404,"IMAGE not found" ),
    ITEM_REGIST_ERROR(0,"상품 등록 중 알 수 없는 에러가 발생하였습니다.");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
