package com.mainproject.be28.domain.shopping.item.service;

import com.mainproject.be28.domain.shopping.item.dto.ItemDto;
import com.mainproject.be28.domain.shopping.item.dto.ItemSearchConditionDto;
import com.mainproject.be28.domain.shopping.item.dto.OnlyItemResponseDto;
import com.mainproject.be28.domain.shopping.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public interface ItemService {
    /*category: 일반 유저 - 상품 조회*/
    Item verifyExistItem(long itemId);
    ItemDto.Response findItem(long itemId);
    Page<OnlyItemResponseDto> findItems(ItemSearchConditionDto condition);

    /*category: 관리자 - 상품 등록 및 수정, 삭제*/
    ItemDto.Response createItem(ItemDto.Post requestBody, List<MultipartFile> itemImgFileList)  throws IOException;
    ItemDto.Response updateItem(ItemDto.Patch requestBody, List<MultipartFile> itemImgFileList) throws IOException;
    void deleteItem(long itemId);


    // 주문이 완료되면 아이템의 재고 수량을 감소시키는 메서드
    void decreaseItemStock(long itemId, long quantity);
}
