package com.mainproject.be28.member.service;

import com.mainproject.be28.comment.dto.CommentDto;
import com.mainproject.be28.comment.entity.Comment;
import com.mainproject.be28.comment.mapper.CommentMapper;
import com.mainproject.be28.comment.repository.CommentRepository;
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
    public void deleteMember(Long memberId){
        Member member = memberService.findVerifiedMember(memberId);

        memberRepository.delete(member);
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
}
