package com.mainproject.be28.domain.shopping.item.dto;

import com.mainproject.be28.domain.shopping.review.dto.ReviewResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


public class ItemDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Post {
        private Long itemId;
        @NotBlank(message = "상품명을 입력해주세요.")
        private String name;
        @Min(100)
        private Long price;
        @NotNull
        private String detail;
        @NotNull
        private Integer stock;
        @NotNull
        private String color;
        @NotNull
        private String brand;
        @NotNull
        private String category;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Patch{
        @Nullable
        private Long itemId;
        @Nullable
        private String name;
        @Nullable
        private Long price;
        @Nullable
        private String detail;
        @Nullable
        private Integer stock;
        @Nullable
        private String color;
        @Nullable
        private String brand;
        @Nullable
        private String category;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private OnlyItemResponseDto item;
        private List<ReviewResponseDto> reviews;
        private List<ItemImageResponseDto> images;
    }
}