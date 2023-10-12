package com.mainproject.be28.domain.shopping.cart.mapper;

import com.mainproject.be28.domain.shopping.cart.dto.CartDto;
import com.mainproject.be28.domain.shopping.cart.entity.Cart;
import com.mainproject.be28.domain.shopping.cart.dto.CartItemResponseDto;

import java.util.List;

public interface CartMapper {

    CartDto.Response cartToCartResponseDto(Cart cart);
    long getTotalPrice(List<CartItemResponseDto> cartItemResponseDtos);
    List<CartItemResponseDto> getCartItemsResponseDto(Cart cart);
}
