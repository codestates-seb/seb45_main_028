package com.mainproject.be28.domain.shopping.review.service;

import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.domain.shopping.item.service.ItemService;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.member.service.MemberService;
import com.mainproject.be28.domain.shopping.review.dto.ReviewPostDto;
import com.mainproject.be28.domain.shopping.review.entity.Review;
import com.mainproject.be28.domain.shopping.review.mapper.ReviewMapper;
import com.mainproject.be28.domain.shopping.review.repository.ReviewRepository;
import com.mainproject.be28.global.utils.CustomBeanUtils;
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
    private final MemberService memberService;
    private final ReviewMapper mapper;
    private final ItemService itemService;


    //리뷰 생성
    public  Review createReview(ReviewPostDto reviewPostDto){
        Review review = new Review();
        Member member = memberService.findTokenMember();
        Item item = itemService.findItem(reviewPostDto.getItemId());
        int score = reviewPostDto.getScore();
        String content = reviewPostDto.getContent();

        review.setScore(score);
        review.setContent(content);
        review.setItem(item);
        review.setMember(member);
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

    public Review findReviewByMember() {
        Member member = memberService.findTokenMember();
        return reviewRepository.save(Review.createReview(member));
    }


}

