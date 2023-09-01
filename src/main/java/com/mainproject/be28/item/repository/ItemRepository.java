package com.mainproject.be28.item.repository;


import com.mainproject.be28.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface ItemRepository extends  JpaRepository<Item, Long> {
}
