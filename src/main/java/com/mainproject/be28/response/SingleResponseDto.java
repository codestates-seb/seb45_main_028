package com.mainproject.be28.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class SingleResponseDto<T> extends BaseResponse{
    private T data;

    public SingleResponseDto(T data, HttpStatus status) {
        this.status = status.value();
        this.message = status.getReasonPhrase();
        this.data = data;
    }
}

