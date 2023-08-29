package com.mainproject.be28.item.dto;

import com.mainproject.be28.review.dto.ReviewResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;


public class ItemDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Post {
        private Long itemId;
        @NotBlank(message = "상품명을 입력해주세요.")
        private String itemName;
        @Min(100)
        private Long itemPrice;
        @NotNull
        private String itemDetail;
        @NotNull
        private String itemStatus;
        @NotNull
        private String itemColor;
        @Positive
        private Double itemScore; // 리뷰 필요
        @NotNull
        private String itemBrand;
        @NotNull
        private String itemCategory;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Patch{
        private Long itemId;
        @NotBlank(message = "상품명을 입력해주세요.")
        private String itemName;
        @Min(100)
        private Long itemPrice;
        @NotNull
        private String itemDetail;
        @NotNull
        private String itemStatus;
        @NotNull
        private String itemColor;
        @Positive
        private Double itemScore; // 리뷰 필요
        @NotNull
        private String itemBrand;
        @NotNull
        private String itemCategory;
    }

    @AllArgsConstructor
    @Getter
    public static class Response {
        private OnlyItemResponseDto item;
        private List<ReviewResponseDto> reviews;
    }
}