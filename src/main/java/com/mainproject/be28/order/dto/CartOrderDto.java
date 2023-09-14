package com.mainproject.be28.order.dto;


import com.mainproject.be28.cartItem.dto.CartItemDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartOrderDto {

    private Long cartItemId;
    private List<CartOrderDto> CartOrderItemList;
}