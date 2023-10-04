package com.mainproject.be28.domain.shopping.order.repository;

import com.mainproject.be28.domain.shopping.order.entity.Order;
import com.mainproject.be28.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findOrderByMember(Member member);

    List<Order> findByMemberOrderByCreatedAtDesc(Member member);
}

