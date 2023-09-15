package com.mainproject.be28.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class MemberPostDto {
    @Id
    private Long MemberId;

    @NotBlank(message = "비밀번호는 공백이 아니어야 합니다.")
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    private String address;

}
