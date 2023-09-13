package com.mainproject.be28.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
public class PasswordPatchDto {
    @NotNull
    private  String email;
    @NotNull
    private String password;
    @NotNull
    //@Pattern(regexp = "(?=.*\\d{1,50})(?=.*[a-zA-Z]{1,50}).{8,20}$")
    private String afterPassword;
}
