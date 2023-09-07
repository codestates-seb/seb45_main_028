package com.mainproject.be28.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemSearchConditionDto {
    @Positive
    private int page;
    @Positive
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
    private String sort;
    @Nullable
    private String order;
/* 아래는 검색조건 설정 미구현된 필드
    private String status;
    private Double score;
*/
}
