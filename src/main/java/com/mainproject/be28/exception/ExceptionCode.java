package com.mainproject.be28.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    MEMBER_NOT_AUTHORIZED(401, "Member not authorized"),
    POST_NOT_FOUND(404, "POST not found"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    ITEM_NOT_FOUND(404, "ITEM not found");



    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
