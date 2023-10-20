package com.mainproject.be28.domain.shopping.cart.service;

import com.mainproject.be28.domain.shopping.cart.dto.CartDto;
import com.mainproject.be28.domain.shopping.cart.dto.CartItemDto;
import com.mainproject.be28.domain.shopping.cart.entity.Cart;
import com.mainproject.be28.domain.shopping.order.entity.OrderItem;

import java.util.List;

public interface CartService {

    CartDto.Response addCart(CartItemDto cartItemDto);
    void removeItem(long itemId);
    void removeAllItem();
    Cart findCartByMember();

    long getTotalPrice(List<OrderItem> orderItems);
}
