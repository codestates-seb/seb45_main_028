package com.mainproject.be28.order.dto;


import com.mainproject.be28.cartItem.dto.CartItemDto;
import com.mainproject.be28.cartItem.entity.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartOrderDto {
    private Long memberId;
    private Long cartItemId;
    private List<CartItem> CartOrderItemList;
}