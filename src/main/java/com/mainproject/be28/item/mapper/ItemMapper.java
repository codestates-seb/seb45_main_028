package com.mainproject.be28.item.mapper;

import com.mainproject.be28.item.dto.ItemDto;
import com.mainproject.be28.item.dto.OnlyItemResponseDto;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.review.dto.ReviewResponseDto;
import com.mainproject.be28.review.entity.Review;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item itemPostDtoToItem(ItemDto.Post itemPostDto);
    Item  itemPatchDtoToItem(ItemDto.Patch itemPatchDto);
    OnlyItemResponseDto itemToOnlyItemResponseDto(Item item);
    List<OnlyItemResponseDto> itemsToOnlyItemResponseDtos(List<Item> items);

    default ItemDto.Response itemToItemResponseDto(Item item) {

        OnlyItemResponseDto onlyitemResponseDto = itemToOnlyItemResponseDto(item);
        List<ReviewResponseDto> reviewResponseDtos = getReviewsResponseDto(item);

        return new ItemDto.Response(onlyitemResponseDto, reviewResponseDtos);
    }

    default List<ReviewResponseDto> getReviewsResponseDto(Item item) {
        List<ReviewResponseDto> reviewResponseDtos = new ArrayList<>();
        List<Review> reviewList =  item.getReviews();
        if(reviewList != null) {
            for (Review review : reviewList) {
                ReviewResponseDto reviewResponseDto =
                        ReviewResponseDto.builder()
                                .reviewId(review.getReviewId())
                                .itemId(review.getItem().getItemId())
                                .memberId(review.getMember().getMemberId())
                                .name(review.getMember().getName())
                                .content(review.getContent())
                                .score(review.getScore())
                                .createdAt(review.getCreatedAt())
                                .likeCount(review.getLikeCount())
                                .unlikeCount(review.getUnlikeCount())
                                .build();
                reviewResponseDtos.add(reviewResponseDto);
            }
        }
        return reviewResponseDtos;
    }
}