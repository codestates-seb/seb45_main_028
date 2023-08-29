package com.mainproject.be28.item.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class OnlyItemResponseDto {
    private String itemName;
    private Long itemPrice;
    private String itemDetail;
    private String itemStatus;
    private String itemColor;
    private Double itemScore;
    private String itemBrand;
    private String itemCategory;
}
