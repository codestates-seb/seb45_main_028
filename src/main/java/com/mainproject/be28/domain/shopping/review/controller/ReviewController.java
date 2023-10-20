package com.mainproject.be28.domain.shopping.review.controller;

import com.mainproject.be28.domain.shopping.review.dto.ReviewPatchDto;
import com.mainproject.be28.domain.shopping.review.dto.ReviewPostDto;
import com.mainproject.be28.domain.shopping.review.entity.Review;
import com.mainproject.be28.domain.shopping.review.mapper.ReviewMapper;
import com.mainproject.be28.domain.shopping.service.ShoppingService;
import com.mainproject.be28.global.response.SingleResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;



@RestController
@RequestMapping("/item/{item-id}/review")
@Validated
@Slf4j
@AllArgsConstructor
public class ReviewController {
    private final ShoppingService shoppingService;
    private final ReviewMapper mapper;
    private  final HttpStatus ok = HttpStatus.OK;



    @PostMapping("/new/{score}")// 리뷰등록
    public ResponseEntity createReview(@PathVariable("item-id")long itemId,
                                       @RequestBody @Valid ReviewPostDto reviewPostDto,
                                       @PathVariable("score") int score) {
        reviewPostDto.setScore(score);
        Review review = shoppingService.createReview(reviewPostDto);
        SingleResponseDto response = new SingleResponseDto<>(mapper.reviewToReviewResponseDto(review),ok);
        return new ResponseEntity(response, ok);

    }
    @PatchMapping("/{review-id}")// 리뷰수정
    public ResponseEntity patchReview(@PathVariable("review-id") @Positive long reviewId,
                                      @Valid @RequestBody ReviewPatchDto reviewPatchDto){
        reviewPatchDto.setReviewId(reviewId);
        Review review = mapper.reviewPatchDtoToReview(reviewPatchDto);
        Review response = shoppingService.updateReview(review);
        SingleResponseDto responses = new SingleResponseDto<>(mapper.reviewToReviewResponseDto(review),ok);
        return new ResponseEntity(responses, ok);
    }
    @DeleteMapping("/{review-id}") //리뷰삭제
    public ResponseEntity deleteReview(@PathVariable("review-id") @Positive long reviewId){
        shoppingService.deleteReview(reviewId);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/{review-id}/like")//리뷰 좋아요
    public ResponseEntity addLike(@PathVariable("review-id") Long reviewId) {
        Review likeReview = shoppingService.addLike(reviewId);
        SingleResponseDto response = new SingleResponseDto<>(mapper.reviewToReviewResponseDto(likeReview),ok);
        return new ResponseEntity(response, ok);
        }

    @PostMapping("/{review-id}/unlike")
    public ResponseEntity addDislike(@PathVariable("review-id") Long reviewId) {
            Review unlikeReview = shoppingService.addDislike(reviewId);
        SingleResponseDto response = new SingleResponseDto<>(mapper.reviewToReviewResponseDto(unlikeReview),ok);
        return new ResponseEntity(response, ok);
        }

}


