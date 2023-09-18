package com.mainproject.be28.order.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPostDto { // 장바구니에서 주문으로 넘어갈 때 필요 (Order 엔티티 생성)
    @PositiveOrZero
    private long itemId;

    @NotNull
    @Min(value = 1, message = "최소 주문 수량은 1개 입니다.")
    private Long quantity;
}
