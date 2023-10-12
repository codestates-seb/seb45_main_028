package com.mainproject.be28.domain.shopping.order.dto;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@AllArgsConstructor
public class OrderPostDto { // 상품에서 바로 주문
    @PositiveOrZero
    private long itemId;

    @NotNull
    @Min(value = 1, message = "최소 주문 수량은 1개 입니다.")
    private Long quantity;
}
