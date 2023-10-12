package com.mainproject.be28.domain.member.service.Layer2;

import com.mainproject.be28.domain.community.comment.dto.CommentResponseDto;
import com.mainproject.be28.domain.community.comment.service.CommentService;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponseDto;
import com.mainproject.be28.domain.shopping.complain.service.ComplainService;
import com.mainproject.be28.domain.shopping.review.dto.ReviewResponseDto;
import com.mainproject.be28.domain.shopping.review.service.ReviewService;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Getter
@Service
public class GetMineServiceImpl  implements GetMineService{
    private final ReviewService reviewService;
    private final CommentService commentService;
    private final ComplainService complainService;
    private final MemberVerifyService memberVerifyService;

    public GetMineServiceImpl(ReviewService reviewService, CommentService commentService, ComplainService complainService, MemberVerifyService memberVerifyService) {
        this.reviewService = reviewService;
        this.commentService = commentService;
        this.complainService = complainService;
        this.memberVerifyService = memberVerifyService;
    }
    public Page<ReviewResponseDto> getMyReviews(int page, int size) {
        String name = memberVerifyService.findTokenMember().getName();
        return reviewService.findReviewByMember(name, page, size);
    }

    //작성한 댓글 조회
    public Page<CommentResponseDto> getMyComments(int page, int size) {
        String name = memberVerifyService.findTokenMember().getName();
        return commentService.findCommentsByMember(name, page, size);
    }
    //작성한 문의 조회
    public Page<ComplainResponseDto> getMyComplains(int page, int size) {
        String name = memberVerifyService.findTokenMember().getName();
        return complainService.findComplainsByMember(name, page, size);
    }
}
