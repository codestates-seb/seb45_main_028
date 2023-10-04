package com.mainproject.be28.global.exception;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {
    @Getter
    private ExceptionCode exceptionCode;
    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}