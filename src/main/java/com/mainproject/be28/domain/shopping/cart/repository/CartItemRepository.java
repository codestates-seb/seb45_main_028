package com.mainproject.be28.domain.shopping.cart.repository;

import com.mainproject.be28.domain.shopping.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findCartItemByCart_CartIdAndItem_ItemId(long cartId, long itemId);

}
