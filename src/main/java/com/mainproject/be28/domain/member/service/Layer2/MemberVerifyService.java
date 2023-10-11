package com.mainproject.be28.domain.member.service.Layer2;

import com.mainproject.be28.domain.member.entity.Member;

public interface MemberVerifyService {
    void verifyAdmin();
    Member findTokenMember();
    Member verifyEmailPassword(String email, String password);
    void verifyExistsEmail(String email);
}
