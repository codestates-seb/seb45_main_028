package com.mainproject.be28.review.mapper;

import com.mainproject.be28.complain.dto.ComplainPatchDto;
import com.mainproject.be28.complain.entity.Complain;
import com.mainproject.be28.review.dto.ReviewPatchDto;
import com.mainproject.be28.review.dto.ReviewPostDto;
import com.mainproject.be28.review.dto.ReviewResponseDto;
import com.mainproject.be28.review.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review reviewPatchDtoToReview(ReviewPatchDto reviewPatchDto);
    @Mapping(source = "item.name", target ="itemName")
    @Mapping(source = "member.name", target = "memberName")
    ReviewResponseDto reviewToReviewResponseDto(Review review);

}