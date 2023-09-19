package com.mainproject.be28.item.mapper;

import com.mainproject.be28.item.dto.ItemDto;
import com.mainproject.be28.item.dto.OnlyItemResponseDto;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.itemImage.dto.ItemImageResponseDto;
import com.mainproject.be28.itemImage.entity.ItemImage;
import com.mainproject.be28.review.dto.ReviewResponseDto;
import com.mainproject.be28.review.entity.Review;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item itemPostDtoToItem(ItemDto.Post itemPostDto);
    Item  itemPatchDtoToItem(ItemDto.Patch itemPatchDto);
    default OnlyItemResponseDto itemToOnlyItemResponseDto(Item item){
        if ( item == null ) {
            return null;
        }

        OnlyItemResponseDto.OnlyItemResponseDtoBuilder onlyItemResponseDto = OnlyItemResponseDto.builder();

        onlyItemResponseDto.itemId( item.getItemId() );
        onlyItemResponseDto.name( item.getName() );
        onlyItemResponseDto.price( item.getPrice() );
        onlyItemResponseDto.detail( item.getDetail() );
        onlyItemResponseDto.stocks( checkStock(item));
        onlyItemResponseDto.color( item.getColor() );
        onlyItemResponseDto.score( item.getScore() );
        onlyItemResponseDto.brand( item.getBrand() );
        onlyItemResponseDto.category( item.getCategory() );
        onlyItemResponseDto.reviewCount(Math.toIntExact(item.getReviewCount()));
        onlyItemResponseDto.imageURLs(getImageResponseDto(item));
        return onlyItemResponseDto.build();
    }

    public default String checkStock(Item item) {
        if(item.getStock()>10) {return "재고 있음";}
        else if(item.getStock()>0){return String.format("%d개 남음", item.getStock());}
        else return "품절";
    }
    Item onlyItemResponseDtotoItem(OnlyItemResponseDto onlyItemResponseDto);

    default ItemDto.Response itemToItemResponseDto(Item item) {

        OnlyItemResponseDto onlyitemResponseDto = itemToOnlyItemResponseDto(item);

        List<ReviewResponseDto> reviewResponseDtos = getReviewsResponseDto(item);

        return new ItemDto.Response(onlyitemResponseDto, reviewResponseDtos);
    }

    default List<ItemImageResponseDto> getImageResponseDto(Item item){
        List<ItemImageResponseDto> itemImageResponseDtos = new ArrayList<>();
        List<ItemImage> imageList = item.getImages();

        if (imageList != null) {
            for (ItemImage image : imageList) {
                ItemImageResponseDto itemImageResponseDto =
                        ItemImageResponseDto.builder()
                        . itemId(image.getItem().getItemId())
                        .itemImageId(image.getItemImageId())
                        .imageName(image.getImageName())
                        .URL(image.getBaseUrl() + image.getImageName())
                        .representationImage(image.getRepresentationImage())
                        .build();
                itemImageResponseDtos.add(itemImageResponseDto);
            }
        }
        return itemImageResponseDtos;
    }

    default List<ReviewResponseDto> getReviewsResponseDto(Item item) {
        List<ReviewResponseDto> reviewResponseDtos = new ArrayList<>();
        List<Review> reviewList =  item.getReviews();
        if(reviewList != null) {
            for (Review review : reviewList) {
                ReviewResponseDto reviewResponseDto =
                        ReviewResponseDto.builder()
                                .itemName(review.getItem().getName())
                                .memberName(review.getMember().getName())
                                .content(review.getContent())
                                .score(review.getScore()) //상품별점추가
                                .modifiedAt(review.getModifiedAt())//수정일 추가
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