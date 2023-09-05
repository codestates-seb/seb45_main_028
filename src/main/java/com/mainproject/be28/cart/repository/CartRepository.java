package com.mainproject.be28.cart.repository;

import com.mainproject.be28.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartByMember_MemberId(long memberId);

}
