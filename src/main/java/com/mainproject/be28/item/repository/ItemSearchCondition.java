package com.mainproject.be28.item.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemSearchCondition {
    private String category;
    private String brand;
    private String color;
    private Long lowPrice;
    private Long highPrice;
    private String name;
/* 아래는 검색조건 설정 미구현된 필드
    private String status;
    private Double score;
*/
}
