package com.mainproject.be28.orderItem.repository;

import com.mainproject.be28.orderItem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    Optional<OrderItem> findByItem_ItemId(long itemId);
}
