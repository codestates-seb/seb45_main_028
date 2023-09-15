package com.mainproject.be28.cartItem.service;

import com.mainproject.be28.cart.entity.Cart;
import com.mainproject.be28.cartItem.entity.CartItem;
import com.mainproject.be28.cartItem.repository.CartItemRepository;
import com.mainproject.be28.item.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem findCartItem(Cart cart, Item item) {
        return cartItemRepository.findCartItemByCart_CartIdAndItem_ItemId(cart.getCartId(), item.getItemId())
                .orElseGet(() -> cartItemRepository.save(CartItem.createCartItem(cart, item, 0)));
    }
}
