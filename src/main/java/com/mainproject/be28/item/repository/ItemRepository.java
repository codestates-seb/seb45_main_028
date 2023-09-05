package com.mainproject.be28.item.repository;

import com.mainproject.be28.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository  extends  JpaRepository<Item, Long>, CustomItemRepository {
}
