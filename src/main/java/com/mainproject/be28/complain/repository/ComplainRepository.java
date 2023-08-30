package com.mainproject.be28.complain.repository;

import com.mainproject.be28.complain.entity.Complain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplainRepository extends JpaRepository<Complain, Long> {
    @Query(value = "SELECT * FROM complain WHERE item_id = :itemId", nativeQuery = true)
    List<Complain> findReviewsByItemId(Long itemId);
}
