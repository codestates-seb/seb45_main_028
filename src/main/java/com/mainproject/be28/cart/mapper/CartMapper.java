package com.mainproject.be28.cart.mapper;

import com.mainproject.be28.cart.dto.CartDto;
import com.mainproject.be28.cart.entity.Cart;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(source = "memberId", target = "member.memberId")
    Cart cartPostDtoToCart(CartDto.Post cartPostDto);
    @Mapping(source = "memberId", target = "member.memberId")
    Cart cartPatchDtoToCart(CartDto.Patch cartPatchDto);
    CartDto cartToCartResponseDto(Cart cart);
}
