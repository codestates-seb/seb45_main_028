package com.mainproject.be28.review.controller;

import com.mainproject.be28.item.service.ItemService;
import com.mainproject.be28.member.mapper.MemberMapper;
import com.mainproject.be28.member.service.MemberService;
import com.mainproject.be28.review.dto.ReviewPatchDto;
import com.mainproject.be28.review.dto.ReviewPostDto;
import com.mainproject.be28.review.dto.ReviewResponseDto;
import com.mainproject.be28.review.entity.Review;
import com.mainproject.be28.review.mapper.ReviewMapper;
import com.mainproject.be28.review.service.ReviewService;
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
    private final ReviewService reviewService;
    private final ReviewMapper mapper;
    private final ItemService itemService;
    private final MemberMapper memberMapper;
    private final MemberService memberService;


    @PostMapping("/new/{score}")// 리뷰등록
    public ResponseEntity createReview(@PathVariable("item-id")long itemId,
                                       @RequestBody @Valid  ReviewPostDto reviewPostDto,
                                       @PathVariable("score") int score) {
        reviewPostDto.setScore(score);
        reviewPostDto.setItemId(itemId);
       reviewService.createReview(mapper.ReviewPostDtoToReview(reviewPostDto));
        return new ResponseEntity<>( HttpStatus.CREATED);

    }
    @PatchMapping("/{review-id}")// 리뷰수정
    public ResponseEntity patchReview(@PathVariable("review-id") @Positive long reviewId,
                                      @Valid @RequestBody ReviewPatchDto reviewPatchDto){
        reviewPatchDto.setReviewId(reviewId);
        Review review = mapper.reviewPatchDtoToReview(reviewPatchDto);
        Review response = reviewService.updateReview(review);
        return  new ResponseEntity<>(mapper.reviewToReviewResponseDto(response),HttpStatus.OK);
    }
    @DeleteMapping("/{review-id}") //리뷰삭제
    public ResponseEntity deleteReview(@PathVariable("review-id") @Positive long reviewId){
        reviewService.deleteReview(reviewId);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/{review-id}/like")//리뷰 좋아요
    public ResponseEntity addLike(@PathVariable("review-id") Long reviewId) {
        Review likeReview = reviewService.addLike(reviewId);
        return new ResponseEntity<>( mapper.reviewToReviewResponseDto( likeReview), HttpStatus.OK);
        }

    @PostMapping("/{review-id}/unlike")
    public ResponseEntity addDislike(@PathVariable("review-id") Long reviewId) {
            Review unlikeReview = reviewService.addDislike(reviewId);
            return new ResponseEntity<>(mapper.reviewToReviewResponseDto( unlikeReview), HttpStatus.OK);
        }

}


