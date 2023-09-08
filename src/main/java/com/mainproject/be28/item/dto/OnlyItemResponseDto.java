package com.mainproject.be28.item.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class OnlyItemResponseDto {
    private Long itemId;
    private String name;
    private Long price;
    private String detail;
    private String status;
    private String color;
    private Double score;
    private String brand;
    private String category;
    private Integer reviewCount;
}
