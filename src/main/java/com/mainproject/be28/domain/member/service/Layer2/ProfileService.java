package com.mainproject.be28.domain.member.service.Layer2;

import com.mainproject.be28.domain.member.dto.PasswordPatchDto;
import com.mainproject.be28.domain.member.entity.Member;

public interface ProfileService {
    Member createMember(Member member);
    Member updateProfile(Member member, String email, String password);
    void changePassword(PasswordPatchDto passwordPatchDto);
    void deleteMember(String email, String password);
    Member getProfile();

}
