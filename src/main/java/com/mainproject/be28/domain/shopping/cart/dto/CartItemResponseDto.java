package com.mainproject.be28.domain.shopping.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@AllArgsConstructor
@Builder
public class CartItemResponseDto {
    @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
    private long itemId;
    private long cartItemId;
    private String name;
    private long price;
    @Min(value = 1, message = "최소 1개 이상 담아주세요")
    private Long count;
}
