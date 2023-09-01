package com.mainproject.be28.response;

import com.mainproject.be28.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;

    public static ErrorResponse of(ExceptionCode e){
        return new ErrorResponse(e.getStatus(), e.getMessage());
    }
    public static ErrorResponse of(HttpStatus h){
        return new ErrorResponse(h.value(),h.getReasonPhrase());
    }
}
