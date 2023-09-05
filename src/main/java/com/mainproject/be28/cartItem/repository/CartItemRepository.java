package com.mainproject.be28.cartItem.repository;

import com.mainproject.be28.cartItem.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findCartItemByCart_CartIdAndItem_ItemId(long cartId, long itemId);

    CartItem deleteCartItemByCart_CartIdAndItem_ItemId(long cartId, long itemId);

    void deleteByCartItemId(long cartItemId);

    CartItem findByCartItemId(long cartItemId);
}
