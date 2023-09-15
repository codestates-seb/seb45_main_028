package com.mainproject.be28.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemSearchConditionDto {
    @Min(value =1 , message = "page 는 1 이상 입력되어야 합니다.")
    private int page;
    @Min(value = 1, message = "size 는 1 이상 입력되어야 합니다.")
    private int size;
    @Nullable
    private String category;
    @Nullable
    private String brand;
    @Nullable
    private String color;
    @Nullable
    private Long lowPrice;
    @Nullable
    private Long highPrice;
    @Nullable
    private String name;
    @Nullable
    @Pattern(regexp = "^(score|review|name|price)$", message = "정렬 기준은 score, review, name, price 중 하나만 입력 되어야 합니다.")
    private String sort;
    @Nullable
    @Pattern(regexp = "^(asc|desc)$", message = "정렬 순서는 asc,desc 중 하나만 입력 되어야 합니다.")
    private String order;
/* 아래는 검색조건 설정 미구현된 필드
    private String status;
*/
}
