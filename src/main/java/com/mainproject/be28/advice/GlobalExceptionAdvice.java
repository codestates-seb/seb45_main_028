package com.mainproject.be28.advice;

import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
    @RestControllerAdvice
    public class GlobalExceptionAdvice {

        // 비지니스로직 사용자 정의 예외
        @ExceptionHandler
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public ErrorResponse handleBusinessLogicException(BusinessLogicException e) {
            final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());
            response.setStatus(e.getExceptionCode().getStatus());
            response.setMessage(e.getMessage());
            return response;
        }

    // BAD_REQUEST - DTO 애너테이션에 붙인 DTO 위배 이유 (message = "~~~") 출력 목적
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        final ErrorResponse response = ErrorResponse.of(e.getBindingResult());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
        return response;
    }
}
