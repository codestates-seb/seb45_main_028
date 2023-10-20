package com.mainproject.be28.domain.shopping.cart.service;

import com.mainproject.be28.domain.shopping.cart.entity.CartItem;
import com.mainproject.be28.domain.shopping.cart.repository.CartItemRepository;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem saveCartItem(CartItem cartItem){
        return cartItemRepository.save(cartItem);
    }
    public CartItem findCartItem(long cartId, long itemId) {
        return cartItemRepository.findCartItemByCart_CartIdAndItem_ItemId(cartId, itemId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.CART_ITEM_NOT_FOUND));
    }
    public void deleteCartItem(CartItem cartItem){
        cartItemRepository.delete(cartItem);
    }
}
