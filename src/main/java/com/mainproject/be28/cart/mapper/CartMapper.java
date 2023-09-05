package com.mainproject.be28.cart.mapper;

import com.mainproject.be28.cart.dto.CartDto;
import com.mainproject.be28.cart.entity.Cart;
import com.mainproject.be28.cartItem.dto.CartItemDto;
import com.mainproject.be28.cartItem.entity.CartItem;
import com.mainproject.be28.cartItem.repository.CartItemRepository;
import com.mainproject.be28.item.service.ItemService;
import com.mainproject.be28.member.entity.Member;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public interface CartMapper {

//    @Mapping(source = "memberId", target = "member.memberId")
//    Cart cartPostDtoToCart(CartDto.Post cartPostDto);
//    @Mapping(source = "memberId", target = "member.memberId")
//    Cart cartPatchDtoToCart(CartDto.Patch cartPatchDto);

    CartDto.Response cartToCartResponseDto(Cart cart);
    long getTotalPrice(List<CartItemDto> cartItemResponseDtos);
    List<CartItemDto> getCartItemsResponseDto(Cart cart, Member member);
}
