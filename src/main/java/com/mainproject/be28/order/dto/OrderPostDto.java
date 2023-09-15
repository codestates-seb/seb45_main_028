package com.mainproject.be28.order.dto;

import java.util.List;
import java.util.Optional;

import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.orderItem.dto.OrderItemPostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPostDto { // 장바구니에서 주문으로 넘어갈 때 필요 (Order 엔티티 생성)
    private List<OrderItemPostDto> orderItems;


}
