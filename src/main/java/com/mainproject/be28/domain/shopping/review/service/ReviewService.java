package com.mainproject.be28.domain.shopping.review.service;

import com.mainproject.be28.domain.shopping.review.dto.ReviewResponseDto;
import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.shopping.review.dto.ReviewPostDto;
import com.mainproject.be28.domain.shopping.review.entity.Review;
import org.springframework.data.domain.Page;

public interface ReviewService {


    //리뷰 생성
    Review createReview(ReviewPostDto reviewPostDto,Member member);


    //리뷰 수정
    Review updateReview(Review review);
    //리뷰아이디로 리뷰찾기
    Review findReview(long reviewId);
    //리뷰 삭제
    void deleteReview(long reviewId);
    //리뷰 좋아요
    Review addLike(Long reviewId) ;
    //리뷰 싫어요
    Review addDislike(Long reviewId) ;

    Page<ReviewResponseDto> findReviewByMember(String name, int page, int size) ;
}

