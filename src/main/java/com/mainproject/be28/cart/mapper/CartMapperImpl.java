package com.mainproject.be28.cart.mapper;

import com.mainproject.be28.cart.dto.CartDto;
import com.mainproject.be28.cart.entity.Cart;
import com.mainproject.be28.cartItem.dto.CartItemDto;
import com.mainproject.be28.cartItem.entity.CartItem;
import com.mainproject.be28.cartItem.repository.CartItemRepository;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.repository.MemberRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
// 빈 카트 조회 시 NullPointException 해결필요
@Component
public class CartMapperImpl implements CartMapper{
    public final CartItemRepository cartItemRepository;
    public final MemberRepository memberRepository;

    public CartMapperImpl(CartItemRepository cartItemRepository, MemberRepository memberRepository) {
        this.cartItemRepository = cartItemRepository;
        this.memberRepository = memberRepository;
    }


    public CartDto.Response cartToCartResponseDto(Cart cart) {
        List<CartItemDto> cartItemResponseDtos = getCartItemsResponseDto(cart, cart.getMember());
        long price = getTotalPrice(cartItemResponseDtos);
        return new CartDto.Response(cartItemResponseDtos, price);
    }

    public long getTotalPrice(List<CartItemDto> cartItemResponseDtos) {
        long price = 0;
        for(CartItemDto cartItemDto : cartItemResponseDtos){
            price += cartItemDto.getPrice()*cartItemDto.getCount();
        }
        return price;
    }
    public List<CartItemDto> getCartItemsResponseDto(Cart cart,Member member) {
        if(cart == null){
            Cart.createCart(member);
        }
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems != null) {
            for (CartItem cartItem : cartItems) {
                if (cartItem.getCount() != 0) {
                    CartItemDto cartItemDto =
                            CartItemDto.builder()
                                    .cartItemId(cartItem.getCartItemId())
                                    .itemId(cartItem.getItem().getItemId())
                                    .count(cartItem.getCount())
                                    .name(cartItem.getItem().getName())
                                    .price(cartItem.getItem().getPrice())
                                    .build();
                    cartItemDtos.add(cartItemDto);
                }
            }
        }
        return cartItemDtos;
    }
}
