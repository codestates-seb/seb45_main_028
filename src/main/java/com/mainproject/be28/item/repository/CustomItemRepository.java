package com.mainproject.be28.item.repository;

import com.mainproject.be28.item.dto.ItemSearchConditionDto;
import com.mainproject.be28.item.dto.OnlyItemResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomItemRepository {
    List<OnlyItemResponseDto> searchByCondition(ItemSearchConditionDto condition, Pageable pageable); // 다중 검색 조건 메서드.  Impl 파일에서 메서드 구현

}
