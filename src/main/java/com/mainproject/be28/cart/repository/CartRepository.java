package com.mainproject.be28.cart.repository;

import com.mainproject.be28.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
//    Service에서 구현할 로그인한 회원의 모든 장바구니 삭제 기능
//     void deleteAllByMemberId(Long memberId);
//    Cart findByMemberId(Long memberId);
}
