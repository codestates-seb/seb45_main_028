package com.mainproject.be28.global.exception;

import lombok.Getter;

@Getter
public final class BusinessLogicException extends RuntimeException {
    @Getter
    private final ExceptionCode exceptionCode;
    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}