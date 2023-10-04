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
    Item findItem(long itemId);
    Page<OnlyItemResponseDto> findItems(ItemSearchConditionDto condition);
    Item createItem(ItemDto.Post requestBody, List<MultipartFile> itemImgFileList)  throws IOException;
    Item updateItem(ItemDto.Patch requestBody, List<MultipartFile> itemImgFileList) throws IOException;
    void deleteItem(long itemId);

}
