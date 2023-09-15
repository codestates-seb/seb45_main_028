package com.mainproject.be28.orderItem.repository;

import com.mainproject.be28.orderItem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
