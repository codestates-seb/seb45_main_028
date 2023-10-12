package com.mainproject.be28.domain.shopping.order.repository;

import com.mainproject.be28.domain.shopping.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    Optional<OrderItem> findByItem_ItemId(long itemId);
}
