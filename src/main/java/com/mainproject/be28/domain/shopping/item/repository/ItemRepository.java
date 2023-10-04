package com.mainproject.be28.domain.shopping.item.repository;

import com.mainproject.be28.domain.shopping.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository  extends  JpaRepository<Item, Long>, CustomItemRepository {
    Item findItemByName(String name);
}
