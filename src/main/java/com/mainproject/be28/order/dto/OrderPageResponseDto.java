package com.mainproject.be28.order.dto;

import com.mainproject.be28.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class OrderPageResponseDto { // 결제창에서 넘겨줄 값
    private String orderNumber; // 주문번호
    private String address;
    private String name;
    private String phone;
    private Long totalPrice; // 총 결제금액
    private String email; // 주문자 이메일
}
