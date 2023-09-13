package com.mainproject.be28.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
public class MemberResponseDto {
    @Id
    private Long MemberId;
    private String password;
    private String email;
    private String name;
    private String phone;
    private String address;

    private Stamp stamp;

    public int getStamp() {
        return stamp.getStampCount();
    }

}
