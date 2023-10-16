package com.mainproject.be28.review.repository;

import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.order.entity.Order;
import com.mainproject.be28.review.dto.ReviewResponseDto;
import com.mainproject.be28.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findAllByMember_MemberId(long memberId);



}
