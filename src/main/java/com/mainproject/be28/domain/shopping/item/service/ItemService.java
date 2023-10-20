package com.mainproject.be28.domain.shopping.item.service;

import com.mainproject.be28.domain.shopping.item.dto.ItemDto;
import com.mainproject.be28.domain.shopping.item.dto.ItemSearchConditionDto;
import com.mainproject.be28.domain.shopping.item.dto.OnlyItemResponseDto;
import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public interface ItemService {
    ItemDto.Response findItem(long itemId);
    Page<OnlyItemResponseDto> findItems(ItemSearchConditionDto condition);

    /*category: 관리자 - 상품 등록 및 수정, 삭제*/
    ItemDto.Response createItem(ItemDto.Post requestBody, List<MultipartFile> itemImgFileList)  throws IOException;
    ItemDto.Response updateItem(ItemDto.Patch requestBody, List<MultipartFile> itemImgFileList) throws IOException;

    void deleteItem(long id);
    // 하위계층 메서드
    default void decreaseItemStock(long itemId, long quantity){
        Item item = verifyExistItem(itemId);
        // 현재 재고 수량이 주문 수량보다 많아야 함을 확인
        if (item.getStock() >= quantity) {
            item.setStock((int) (item.getStock() - quantity));
            save(item);
        } else {
            throw new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND);//재고 부족 에러코드만들기
        }
    }
    Item verifyExistItem(long itemId);
    // 내부 메서드
    void  save(Item item) ;
    }

