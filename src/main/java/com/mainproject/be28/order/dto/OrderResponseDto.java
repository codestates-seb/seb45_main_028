package com.mainproject.be28.order.dto;


import com.mainproject.be28.orderItem.dto.OrderItemResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto { //주문 목록 조회시 필요
    private String name;
    private String orderNumber;
    private LocalDateTime createdAt;
    private String orderStatus;
    private Long totalPrice;
    private List<OrderItemResponseDto> items;
}