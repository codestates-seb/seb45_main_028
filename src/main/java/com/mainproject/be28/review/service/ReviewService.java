package com.mainproject.be28.review.service;

import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.review.dto.ReviewResponseDto;
import com.mainproject.be28.review.entity.Review;
import com.mainproject.be28.review.repository.ReviewRepository;
import com.mainproject.be28.utils.CustomBeanUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomBeanUtils<Review> beanUtils;

    //리뷰 생성
    public  Review createReview(Review review){
        return reviewRepository.save(review);
    }

    //리뷰 수정
    public Review updateReview(Review review) {
        Review findReview = findReview(review.getReviewId());

        Review updatedReview =
                beanUtils.copyNonNullProperties(review, findReview);//complain 객체에서 변경된 부분만을 findComplain 엔티티에 복사
        return reviewRepository.save(updatedReview);

    }
    //리뷰아이디로 리뷰찾기
    public Review findReview(long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        return review.orElseThrow(() -> new BusinessLogicException(ExceptionCode.Review_NOT_FOUND));
    }
    //리뷰 삭제
    public void deleteReview(long reviewId){
        Review findReview = findReview(reviewId);
        reviewRepository.delete(findReview);
    }
    //리뷰 좋아요
    public Review addLike(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            review.setLikeCount(review.getLikeCount() + 1);
            return reviewRepository.save(review);
        }
        return null;
    }
    //리뷰 싫어요
    public Review addDislike(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            review.setUnlikeCount(review.getUnlikeCount() + 1);
            return reviewRepository.save(review);
        }
        return null;
    }
}

