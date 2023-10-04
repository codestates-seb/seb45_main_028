package com.mainproject.be28.domain.shopping.review.mapper;

import com.mainproject.be28.domain.shopping.review.dto.ReviewPatchDto;
import com.mainproject.be28.domain.shopping.review.dto.ReviewResponseDto;
import com.mainproject.be28.domain.shopping.review.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review reviewPatchDtoToReview(ReviewPatchDto reviewPatchDto);
    @Mapping(source = "item.name", target ="itemName")
    @Mapping(source = "member.name", target = "memberName")
    ReviewResponseDto reviewToReviewResponseDto(Review review);

}