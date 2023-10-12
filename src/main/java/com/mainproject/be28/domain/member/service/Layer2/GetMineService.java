package com.mainproject.be28.domain.member.service.Layer2;

import com.mainproject.be28.domain.community.comment.dto.CommentResponseDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponseDto;
import com.mainproject.be28.domain.shopping.review.dto.ReviewResponseDto;
import org.springframework.data.domain.Page;

public interface GetMineService {
    Page<ReviewResponseDto> getMyReviews(int page, int size);
    Page<CommentResponseDto> getMyComments(int page, int size);
    Page<ComplainResponseDto> getMyComplains(int page, int size);
}
