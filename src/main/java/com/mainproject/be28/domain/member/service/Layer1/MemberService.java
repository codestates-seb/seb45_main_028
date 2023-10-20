package com.mainproject.be28.domain.member.service.Layer1;

import com.mainproject.be28.domain.member.dto.MemberPatchDto;
import com.mainproject.be28.domain.member.dto.MemberPostDto;
import com.mainproject.be28.domain.member.dto.MemberResponseDto;
import com.mainproject.be28.domain.member.dto.PasswordPatchDto;
import com.mainproject.be28.domain.member.entity.Member;
import org.springframework.data.domain.Page;

public interface MemberService {
    MemberResponseDto createMember(MemberPostDto memberPostDto);
    void createMember(Member member);
    MemberResponseDto updateProfile(MemberPatchDto requestBody);
    void changePassword(PasswordPatchDto passwordPatchDto);
    void deleteMember(String email, String password);
    MemberResponseDto getProfile();

    <T> Page<T> getMine(int page, int size, Class<T> condition);
//    Page<ReviewResponseDto> getMyReviews(int page, int size);
//    Page<CommentResponseDto> getMyComments(int page, int size);
//    Page<ComplainResponsesDto> getMyComplains(int page, int size);
}
