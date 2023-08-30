package com.mainproject.be28.complain.mapper;

import com.mainproject.be28.complain.dto.ComplainDto;
import com.mainproject.be28.complain.dto.ComplainPostDto;
import com.mainproject.be28.complain.entity.Complain;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.review.entity.Review;

@Mapper(componentModel = "spring")
public interface ComplainMapper {
    default Complain complainPostDtoToComplain(ComplainPostDto complainPostDto) {
        Complain complain = new Complain();
        Member member = new Member();
        member.setMemberId(complainPostDto.getMemberId());
        Item item = new Item();
        item.setItemId(complainPostDto.getItemId());

        complain.setItem(item);
        complain.setMember(member);
        complain.setContent(complainPostDto.getContent());

        return complain;
    }

    default Review reviewPatchDtoToReview(ReviewPatchDto reviewPatchDto) {
        Review review = new Review();
        Member member = new Member();
        member.setMemberId(reviewPatchDto.getMemberId());

        review.setMember(member);
        review.setReviewRating(reviewPatchDto.getReviewRating());
        review.setReviewTitle(reviewPatchDto.getReviewTitle());
        review.setReviewContent(reviewPatchDto.getReviewContent());

        return review;
    }

    default ReviewResponseDto.ReviewDto reviewToReviewResponseDto(Review review, Item item) {
        return ReviewResponseDto.ReviewDto.builder()
                .memberId(review.getMember().getMemberId())
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .reviewRating(review.getReviewRating())
                .itemTitle(item.getItemTitle())
                .titleImageURL(item.getTitleImageUrl())
                .build();
    }
}
