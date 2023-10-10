package com.mainproject.be28.domain.shopping.item.repository;

import com.mainproject.be28.domain.shopping.item.dto.ItemSearchConditionDto;
import com.mainproject.be28.domain.shopping.item.entity.Item;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomItemRepository {
    List<Item> searchByCondition(ItemSearchConditionDto condition, Pageable pageable); // 다중 검색 조건 메서드.  Impl 파일에서 메서드 구현

}
