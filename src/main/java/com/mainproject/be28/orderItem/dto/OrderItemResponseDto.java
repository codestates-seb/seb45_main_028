package com.mainproject.be28.orderItem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderItemResponseDto {  // 주문 목록 조회시 필요
    private String ItemName;
    private Long price;
    private Long quantity;
}