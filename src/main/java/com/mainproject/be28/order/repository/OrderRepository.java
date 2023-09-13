package com.mainproject.be28.order.repository;

import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.order.data.OrderStatus;
import com.mainproject.be28.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findByMemberOrderByCreatedAtDesc(Member member);
}

