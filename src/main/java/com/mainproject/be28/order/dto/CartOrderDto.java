package com.mainproject.be28.order.dto;


import com.mainproject.be28.cartItem.dto.CartItemDto;
import com.mainproject.be28.cartItem.dto.CartItemResponseDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.util.List;

@Getter
@Setter
public class CartOrderDto {
    private long cartItemId;
    @Min(value = 1, message = "최소 1개 이상 담아주세요")
    private Long count;
}