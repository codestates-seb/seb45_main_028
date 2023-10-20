package com.mainproject.be28.domain.member.service.Layer2;

import com.mainproject.be28.domain.community.comment.dto.CommentResponseDto;
import com.mainproject.be28.domain.community.comment.service.CommentService;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponsesDto;
import com.mainproject.be28.domain.shopping.review.dto.ReviewResponseDto;
import com.mainproject.be28.domain.shopping.service.ShoppingService;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

public interface GetMineService {
    <T> Page<T> getMine(int page, int size,  Class<T> responseType);
//    Page<ReviewResponseDto> getMyReviews(int page, int size);
//    Page<CommentResponseDto> getMyComments(int page, int size);
//    Page<ComplainResponsesDto> getMyComplains(int page, int size);
}

@Getter @RequiredArgsConstructor
@Service
class GetMineServiceImpl  implements GetMineService {
    private final ShoppingService shoppingService;
    private final MemberVerifyService memberVerifyService;
    private final CommentService commentService;

    @Override
    public <T> Page<T> getMine(int page, int size, Class<T> responseType) {

        //fixme: 다형성으로 변경 필요

        try {
            if (responseType.equals(ReviewResponseDto.class)) {
                return  (Page<T>) shoppingService.findReviewByMember(getMemberName(), page, size);
            } else if (responseType.equals(CommentResponseDto.class)) {
                return (Page<T>)  commentService.findCommentsByMember(getMemberName(), page, size);
            } else if (responseType.equals(ComplainResponsesDto.class)) {
                return (Page<T>) shoppingService.findComplainsByMember(getMemberName(), page, size);
            } else {
                throw new BusinessLogicException(ExceptionCode.NOT_FOUND);
            }
        } catch(ClassCastException e){
            throw new RuntimeException(e.getMessage(),e.getCause());
        }
    }
//    public Page<ReviewResponseDto> getMyReviews(int page, int size) {
//        return shoppingService.findReviewByMember(getMemberName(), page, size);
//    }
//    public Page<CommentResponseDto> getMyComments(int page, int size) {
//        return commentService.findCommentsByMember(getMemberName(), page, size);
//    }
//    public Page<ComplainResponsesDto> getMyComplains(int page, int size) {
//        return shoppingService.findComplainsByMember(getMemberName(), page, size);
//    }
    private String getMemberName() {
        return memberVerifyService.findTokenMember().getName();
    }
}