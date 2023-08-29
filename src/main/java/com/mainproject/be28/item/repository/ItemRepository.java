package com.mainproject.be28.item.repository;

import com.mainproject.be28.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface ItemRepository extends  JpaRepository<Item, Long> {
        Optional<Item> findItemByItemId(long itemId);

//        @Query("SELECT m.memberId FROM Member m WHERE m.email = :email")
//        Long findMemberIdByEmail(String email);
    }
