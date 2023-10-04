package com.mainproject.be28.domain.shopping.cart.repository;

import com.mainproject.be28.domain.shopping.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findCartItemByCart_CartIdAndItem_ItemId(long cartId, long itemId);

    CartItem deleteCartItemByCart_CartIdAndItem_ItemId(long cartId, long itemId);

    void deleteByCartItemId(long cartItemId);

    CartItem findByCartItemId(long cartItemId);
}
