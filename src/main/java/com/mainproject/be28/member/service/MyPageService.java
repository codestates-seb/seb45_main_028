package com.mainproject.be28.member.service;

import com.mainproject.be28.comment.dto.CommentDto;
import com.mainproject.be28.comment.entity.Comment;
import com.mainproject.be28.comment.mapper.CommentMapper;
import com.mainproject.be28.comment.repository.CommentRepository;
import com.mainproject.be28.complain.dto.ComplainResponseDto;
import com.mainproject.be28.complain.entity.Complain;
import com.mainproject.be28.complain.mapper.ComplainMapper;
import com.mainproject.be28.complain.repository.ComplainRepository;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.member.dto.MemberPatchDto;
import com.mainproject.be28.member.dto.PasswordPatchDto;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.mapper.MemberMapper;
import com.mainproject.be28.member.repository.MemberRepository;
import com.mainproject.be28.review.dto.ReviewResponseDto;
import com.mainproject.be28.review.entity.Review;
import com.mainproject.be28.review.mapper.ReviewMapper;
import com.mainproject.be28.review.repository.ReviewRepository;
import com.mainproject.be28.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MyPageService {
    private final MemberMapper mapper;
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
    public Member updateProfile(MemberPatchDto requestBody) {  // 주소, 핸드폰번호 변경.
        Member findMember = memberService.verifyEmailPassword(requestBody.getEmail(), requestBody.getPassword());
        Member member = mapper.memberPatchToMember(requestBody);

        Member updatedMember =
                beanUtils.copyNonNullProperties(member, findMember);

        return memberRepository.save(updatedMember);
    }

    public Member changePassword(PasswordPatchDto passwordPatchDto) {
        Member findMember = memberService.verifyEmailPassword(passwordPatchDto.getEmail(), passwordPatchDto.getPassword());
        matchPassword(passwordPatchDto);
        String encodedPassword = passwordEncoder.encode(passwordPatchDto.getAfterPassword());
        findMember.setPassword(encodedPassword);

        return findMember;
    }

    private static void matchPassword(PasswordPatchDto passwordPatchDto) {
        boolean matchNewPassword = passwordPatchDto.getAfterPassword().equals(passwordPatchDto.getConfirmPassword());
        if(!matchNewPassword){ throw new BusinessLogicException(ExceptionCode.DO_NOT_MATCH_PASSWORD);}
    }

    //회원 탈퇴
    public void deleteMember(String email, String password){
        Member member = memberService.verifyEmailPassword(email,password);
        memberRepository.delete(member);
    }

    //작성한 리뷰 조회
    public Page<ReviewResponseDto> getMyReviews(long memberId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        List<Review> myReviews = reviewRepository.findAllByMember_MemberId(memberId);
        List<ReviewResponseDto> myReviewsDto = new ArrayList<>();
        for (Review review : myReviews) {
            myReviewsDto.add(reviewMapper.reviewToReviewResponseDto(review));
        }

        return new PageImpl<>(myReviewsDto, pageRequest, myReviewsDto.size());
    }

    //작성한 댓글 조회
    public Page<CommentDto> getMyComments(int page, int size) {
        long memberId = memberService.findTokenMemberId();
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        List<Comment> myComments = commentRepository.findCommentsByMember_MemberId(memberId);
        List<CommentDto> myCommentsDto = new ArrayList<>();
        for (Comment comment : myComments) {
            myCommentsDto.add(commentMapper.commentToCommentDto(comment));
        }
        return new PageImpl<>(myCommentsDto, pageRequest, myCommentsDto.size());
    }

    //작성한 문의 조회
    public Page<ComplainResponseDto> getMyComplains(int page, int size) {
        long memberId = memberService.findTokenMemberId();
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        List<Complain> myComplains = complainRepository.findAllByMember_MemberId(memberId);
        List<ComplainResponseDto> myComplainsDto = new ArrayList<>();
        for (Complain complain : myComplains) {
            myComplainsDto.add(complainMapper.complainToComplainResponseDto(complain));
        }
        return new PageImpl<>(myComplainsDto, pageRequest, myComplainsDto.size());
    }
}
