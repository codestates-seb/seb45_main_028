package com.mainproject.be28.item.repository;

import com.mainproject.be28.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository  extends  JpaRepository<Item, Long>, CustomItemRepository {
    Page<Item> findAllByBrand(String brand, PageRequest pageRequest); // JPA 쿼리  메서드, queryDSL과 별개. 동시 사용 가능
}
