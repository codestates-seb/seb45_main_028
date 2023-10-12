package com.mainproject.be28.domain.shopping.cart.service;

import com.mainproject.be28.domain.shopping.cart.dto.CartItemDto;
import com.mainproject.be28.domain.shopping.cart.entity.Cart;
import com.mainproject.be28.domain.shopping.order.entity.OrderItem;

import java.util.List;

public interface CartService {
    Cart addCart(CartItemDto cartItemDto);
    Cart findCartByMember();
    void removeItem(long itemId);
    void removeAllItem();
    long getTotalPrice(List<OrderItem> orderItems);

}
