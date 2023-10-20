package com.mainproject.be28.domain.shopping.review.service;

import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.domain.shopping.item.service.ItemService;
import com.mainproject.be28.domain.shopping.review.dto.ReviewPostDto;
import com.mainproject.be28.domain.shopping.review.dto.ReviewResponseDto;
import com.mainproject.be28.domain.shopping.review.entity.Review;
import com.mainproject.be28.domain.shopping.review.mapper.ReviewMapper;
import com.mainproject.be28.domain.shopping.review.repository.ReviewRepository;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import com.mainproject.be28.global.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper mapper;
    private final ItemService itemService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper mapper, ItemService itemService) {
        this.reviewRepository = reviewRepository;
        this.mapper = mapper;
        this.itemService = itemService;
    }

    @Override
    public Review createReview(ReviewPostDto reviewPostDto,Member member) {
        Review review = new Review();
        Item item = itemService.verifyExistItem(reviewPostDto.getItemId());
        int score = reviewPostDto.getScore();
        String content = reviewPostDto.getContent();

        review.setScore(score);
        review.setContent(content);
        review.setItem(item);
        review.setMember(member);
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Review review) {
        Review findReview = findReview(review.getReviewId());
        CustomBeanUtils<Review> beanUtils = new CustomBeanUtils<>();
        Review updatedReview =
                beanUtils.copyNonNullProperties(review, findReview);//complain 객체에서 변경된 부분만을 findComplain 엔티티에 복사
        return reviewRepository.save(updatedReview);
    }

    @Override
    public Review findReview(long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        return review.orElseThrow(() -> new BusinessLogicException(ExceptionCode.Review_NOT_FOUND));
    }

    @Override
    public void deleteReview(long reviewId) {
        Review findReview = findReview(reviewId);
        reviewRepository.delete(findReview);
    }

    @Override
    public Review addLike(Long reviewId){
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            review.setLikeCount(review.getLikeCount() + 1);
            return reviewRepository.save(review);
        }
        return null;
    }

    @Override
    public Review addDislike(Long reviewId){
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            review.setUnlikeCount(review.getUnlikeCount() + 1);
            return reviewRepository.save(review);
        }
        return null;
    }

    @Override
    public Page<ReviewResponseDto> findReviewByMember(String name, int page, int size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        List<Review> reviews = reviewRepository.findAllByMember_Name(name);
        List<ReviewResponseDto> reviewsDto = new ArrayList<>();
        for (Review review : reviews) {
            reviewsDto.add(mapper.reviewToReviewResponseDto(review));
        }
        return new PageImpl<>(reviewsDto, pageRequest, reviewsDto.size());
    }

}
