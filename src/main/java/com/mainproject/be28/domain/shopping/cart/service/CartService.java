package com.mainproject.be28.domain.shopping.cart.service;

import com.mainproject.be28.domain.shopping.cart.entity.Cart;
import com.mainproject.be28.domain.shopping.cart.repository.CartRepository;
import com.mainproject.be28.domain.shopping.cart.dto.CartItemDto;
import com.mainproject.be28.domain.shopping.cart.entity.CartItem;
import com.mainproject.be28.domain.shopping.order.entity.OrderItem;
import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.member.service.MemberService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service @Slf4j
public class CartService {
    private final MemberService memberService;
    private final CartRepository cartRepository;
    private final CartItemService cartItemService;

    public CartService(MemberService memberService, CartRepository cartRepository, CartItemService cartItemService) {
        this.memberService = memberService;
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
    }

    @Transactional
    public Cart addCart(CartItemDto cartItemDto) {
    Cart cart = findCartByMember();
    CartItem cartItem = cartItemService.findCartItem(cart, cartItemDto.getItemId());

    cartItem.addCount(cartItemDto.getCount());
    cartItem.setModifiedAt(LocalDateTime.now());

    addItemInCart(cart, cartItem);
    cartRepository.save(cart);

    return cart;
}
    public Cart findCartByMember() {
        Member member = memberService.findTokenMember();
        return cartRepository.findCartByMember(member).orElseGet(() -> cartRepository.save(Cart.createCart(member)));
    }

    public void removeItem(long itemId) { // 장바구니 내 개별 상품 제거
        Cart cart = findCartByMember();
        CartItem cartItem = cartItemService.findCartItem(cart, itemId);

        cartItemService.deleteCartItem(cartItem);
    }
    public void removeAllItem() { // 장바구니 전체 삭제
        Cart cart = findCartByMember();
        cartRepository.delete(cart);
    }

    public long getTotalPrice(List<OrderItem> orderItems) {
        long price = 0;
        for (OrderItem orderItem : orderItems) {
            price += orderItem.getPrice() * orderItem.getQuantity();
        }
        return price;
    }

    private void addItemInCart(Cart cart, CartItem cartItem) {
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItemService.saveCartItem(cartItem));
        cart.setCartItems(cartItemList);
    }
}
