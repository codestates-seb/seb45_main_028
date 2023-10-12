package com.mainproject.be28.domain.member.service.Layer1;

import com.mainproject.be28.domain.community.comment.dto.CommentResponseDto;
import com.mainproject.be28.domain.member.dto.MemberPatchDto;
import com.mainproject.be28.domain.member.dto.MemberPostDto;
import com.mainproject.be28.domain.member.dto.MemberResponseDto;
import com.mainproject.be28.domain.member.dto.PasswordPatchDto;
import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponseDto;
import com.mainproject.be28.domain.shopping.review.dto.ReviewResponseDto;
import org.springframework.data.domain.Page;

public interface MemberService {
    MemberResponseDto createMember(MemberPostDto memberPostDto);
    void createMember(Member member);
    MemberResponseDto updateProfile(MemberPatchDto requestBody);
    void changePassword(PasswordPatchDto passwordPatchDto);
    void deleteMember(String email, String password);
    MemberResponseDto getProfile();

    Page<ReviewResponseDto> getMyReviews(int page, int size);
    Page<CommentResponseDto> getMyComments(int page, int size);
    Page<ComplainResponseDto> getMyComplains(int page, int size);
}
