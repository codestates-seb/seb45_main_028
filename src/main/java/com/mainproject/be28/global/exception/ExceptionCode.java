package com.mainproject.be28.global.exception;

import lombok.Getter;

public enum ExceptionCode {
    ONLY_ADMIN_CAN(401, "관리자 권한이 필요합니다."),
    NOT_FOUND(404, "not found" ),
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    MEMBER_NOT_AUTHORIZED(401, "Member not authorized"),
    POST_NOT_FOUND(404, "POST not found"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    COMMENT_NOT_FOUND(404, "Answer not found"),
    ITEM_NOT_FOUND(404, "ITEM not found"),
    ITEM_EXIST(409, "동일한 상품명이 존재합니다."),
    CART_NOT_FOUND(404, "CART NOT FOUND"),
    CART_ITEM_NOT_FOUND(404, "장바구니 내 해당 상품이 존재하지 않습니다."),
    BOARD_NOT_FOUND(404, "BOARD not found"),
    Complain_NOT_FOUND(404,"COMPLAIN not found"),
    Review_NOT_FOUND(404,"REVIEW not found" ),
    IMAGE_NOT_FOUND(404,"IMAGE not found" ),
    INCORRECT_PASSWORD(401,"비밀번호가 일치하지 않습니다."),
    IMAGE_NOT_CONVERTED(500, "이미지 변환 실패"),
    INVALID_ORDER_DATA(500,"주문 불가"),
    ORDER_ITME_NO_FOUND(404,"ORDER_ITME_NO_FOUND"),
    ORDER_NOT_FOUND(404,"주문을 찾을 수 없습니다"),
    NOT_ORDER_HOLDER(401, "주문자와 로그인정보가 다릅니다."),
    ALREADY_APPLIED_REFUND(404,"이미 환불이 된 주문입니다"),
    ALREADY_CANCELED(404, "이미 취소된 주문입니다"),
    NOT_YET_PAID(404,"아직 결제가 안된 주문입니다"),
    VERIFY_FAILURE(401,"ID 혹은 PW가 일치하지 않습니다."),
    USER_EXIST(409,"존재하는 사용자 입니다." ),
    DO_NOT_MATCH_PASSWORD(403, "입력한 비밀번호가 일치하지 않습니다.")    ;
    @Getter
    private final int status;

    @Getter
    private final String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
