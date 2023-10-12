package com.mainproject.be28.domain.shopping.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class OrderPageResponseDto { // 결제창에서 넘겨줄 값
    private String orderNumber; // 주문번호
    private String address;
    private String memberName;
    private String phone;
    private Long totalPrice; // 총 결제금액
    private String email; // 주문자 이메일

    private List<OrderItemResponseDto> orderItems;


}
