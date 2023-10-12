package com.mainproject.be28.domain.shopping.review.repository;

import com.mainproject.be28.domain.shopping.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findAllByMember_MemberId(long memberId);
    List<Review> findAllByMember_Name(String name);
}
