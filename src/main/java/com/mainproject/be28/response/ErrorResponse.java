package com.mainproject.be28.response;

import com.mainproject.be28.exception.ExceptionCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class ErrorResponse {
    private int status;
    private String message;
    private List<FieldError> fieldErrors;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(List<FieldError> fieldErrors, String message) {
        this.fieldErrors = fieldErrors;
    }

    public static ErrorResponse of(ExceptionCode e){
        return new ErrorResponse(e.getStatus(), e.getMessage());
    }
    public static ErrorResponse of(HttpStatus h){
        return new ErrorResponse(h.value(),h.getReasonPhrase());
    }
    public static ErrorResponse of(BindingResult bindingResult) {
        return new ErrorResponse(FieldError.of(bindingResult), null);
    }

    @Getter
    public static class FieldError {
        private String field;
        private Object rejectedValue;
        private String reason;

        private FieldError(String field, Object rejectedValue, String reason) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors =
                    bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ?
                                    "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }

}
