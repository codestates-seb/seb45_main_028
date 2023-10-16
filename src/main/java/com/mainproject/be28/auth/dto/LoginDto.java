package com.mainproject.be28.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

    private String email;
    private String password;
}
