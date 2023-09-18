package com.mainproject.be28.member.service;

import com.mainproject.be28.comment.dto.CommentDto;
import com.mainproject.be28.comment.entity.Comment;
import com.mainproject.be28.comment.mapper.CommentMapper;
import com.mainproject.be28.comment.repository.CommentRepository;
import com.mainproject.be28.complain.dto.ComplainResponseDto;
import com.mainproject.be28.complain.entity.Complain;
import com.mainproject.be28.complain.mapper.ComplainMapper;
import com.mainproject.be28.complain.repository.ComplainRepository;
import com.mainproject.be28.member.dto.PasswordPatchDto;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.repository.MemberRepository;
import com.mainproject.be28.review.dto.ReviewResponseDto;
import com.mainproject.be28.review.entity.Review;
import com.mainproject.be28.review.mapper.ReviewMapper;
import com.mainproject.be28.review.repository.ReviewRepository;
import com.mainproject.be28.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MyPageService {
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final CustomBeanUtils<Member> beanUtils;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ComplainRepository complainRepository;
    private final ComplainMapper complainMapper;
    public Member updateProfile(Member member) {  // 주소, 핸드폰번호 변경
        long memberId = memberService.findTokenMemberId();
        Member findMember = memberService.findVerifiedMember(memberId);
        Member updatedMember =
                beanUtils.copyNonNullProperties(member, findMember);

        Member savedMember = memberRepository.save(updatedMember);
        return savedMember;
    }

    public Member changePassword(PasswordPatchDto passwordPatchDto) {
        Member findMember = memberService.findTokenMember();

        findMember.setPassword(passwordPatchDto.getAfterPassword());

        return findMember;
    }

    //회원 탈퇴
    public void deleteMember(Long memberId, String passwordToConfirm){
        Member member = memberService.findVerifiedMember(memberId);
        // 회원의 비밀번호 확인
        if (passwordEncoder.matches(passwordToConfirm, member.getPassword())) {
            memberRepository.delete(member);
        } else {
            // 비밀번호가 일치하지 않는 경우 예외 또는 처리 방법을 선택할 수 있습니다.
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    //작성한 리뷰 조회
    public List<ReviewResponseDto> getMyReviews(long memberId) {
        List<Review> myReviews = reviewRepository.findAllByMember_MemberId(memberId);
        List<ReviewResponseDto> myReviewsDto = new ArrayList<>();
        for (Review review : myReviews) {
            myReviewsDto.add(reviewMapper.reviewToReviewResponseDto(review));
        }
        return myReviewsDto;
    }

    //작성한 댓글 조회
    public List<CommentDto> getMyComments(long memberId) {
        List<Comment> myComments = commentRepository.findCommentsByMember_MemberId(memberId);
        List<CommentDto> myCommentsDto = new ArrayList<>();
        for (Comment comment : myComments) {
            myCommentsDto.add(commentMapper.commentToCommentDto(comment));
        }
      return myCommentsDto;
    }

    //작성한 문의 조회
    public List<ComplainResponseDto> getMyComplains(long memberId) {
        List<Complain> myComplains = complainRepository.findAllByMember_MemberId(memberId);
        List<ComplainResponseDto> myComplainsDto = new ArrayList<>();
        for (Complain complain : myComplains) {
            myComplainsDto.add(complainMapper.complainToComplainResponseDto(complain));
        }
        return myComplainsDto;
    }
}
