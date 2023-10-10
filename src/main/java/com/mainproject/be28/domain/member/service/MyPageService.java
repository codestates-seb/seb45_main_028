package com.mainproject.be28.domain.member.service;

import com.mainproject.be28.domain.community.comment.dto.CommentResponseDto;
import com.mainproject.be28.domain.community.comment.service.CommentService;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponseDto;
import com.mainproject.be28.domain.shopping.complain.service.ComplainService;
import com.mainproject.be28.domain.shopping.review.service.ReviewService;
import com.mainproject.be28.domain.shopping.review.dto.ReviewResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class MyPageService {

    public final MemberService memberService;
    private final CommentService commentService;
    private final ReviewService reviewService;
    private final ComplainService complainService;

    public MyPageService(MemberService memberService, CommentService commentService, ReviewService reviewService, ComplainService complainService) {
        this.memberService = memberService;
        this.commentService = commentService;
        this.reviewService = reviewService;
        this.complainService = complainService;
    }

    //작성한 리뷰 조회
    public Page<ReviewResponseDto> getMyReviews(long memberId, int page, int size) {
        return reviewService.getMyReviews(memberId, page, size);
    }

    //작성한 댓글 조회
    public Page<CommentResponseDto> getMyComments(int page, int size) {
        return commentService.getMyComments(page, size);
    }
    //작성한 문의 조회
    public Page<ComplainResponseDto> getMyComplains(int page, int size) {
        return complainService.getMyComplains(page, size);
    }
}
