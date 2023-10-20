package com.mainproject.be28.domain.shopping.cart.repository;

import com.mainproject.be28.domain.shopping.cart.entity.Cart;
import com.mainproject.be28.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByMember_Name(String name);
    Optional<Cart> findCartByMember(Member member);


}
