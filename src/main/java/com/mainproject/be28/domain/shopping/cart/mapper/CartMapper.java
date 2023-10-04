package com.mainproject.be28.domain.shopping.cart.mapper;

import com.mainproject.be28.domain.shopping.cart.dto.CartDto;
import com.mainproject.be28.domain.shopping.cart.entity.Cart;
import com.mainproject.be28.domain.shopping.cart.dto.CartItemResponseDto;

import java.util.List;

public interface CartMapper {

//    @Mapping(source = "memberId", target = "member.memberId")
//    Cart cartPostDtoToCart(CartDto.Post cartPostDto);
//    @Mapping(source = "memberId", target = "member.memberId")
//    Cart cartPatchDtoToCart(CartDto.Patch cartPatchDto);

    CartDto.Response cartToCartResponseDto(Cart cart);
    long getTotalPrice(List<CartItemResponseDto> cartItemResponseDtos);
    List<CartItemResponseDto> getCartItemsResponseDto(Cart cart);
}
