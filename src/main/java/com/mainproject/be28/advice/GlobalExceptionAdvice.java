package com.mainproject.be28.advice;

import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

    @Slf4j
    @RestControllerAdvice
    public class GlobalExceptionAdvice {

        // 비지니스로직 사용자 정의 예외
        @ExceptionHandler
        public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {
            final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

            return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode()
                    .getStatus()));
        }

}
