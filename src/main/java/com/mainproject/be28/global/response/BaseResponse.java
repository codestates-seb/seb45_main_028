package com.mainproject.be28.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public class BaseResponse {
    protected int status;
    protected String message;

}
