package com.mainproject.be28.item.service;

import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.item.repository.ItemRepository;
import com.mainproject.be28.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CustomBeanUtils<Item> beanUtils;

    public ItemService(ItemRepository itemRepository, CustomBeanUtils<Item> beanUtils) {
        this.itemRepository = itemRepository;
        this.beanUtils = beanUtils;
    }

    public Item createItem(Item item){

        return itemRepository.save(item);

    }

    public Item updateItem(Item item) {
       Item findItem = verifyExistItem(item.getItemId());

        // 관리자만 수정 권한 기능 추가 필요

         Item updatedItem =
                    beanUtils.copyNonNullProperties(item, findItem);
            return itemRepository.save(updatedItem);

    }

    public Item findItem(long itemId){

        Optional<Item> optionalItem =
                itemRepository.findById(itemId);
        //리뷰 수,  평점
        Item findItem = optionalItem.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND));


        return findItem;
    }

    public Page<Item> findItems(int page, int size){

        Page<Item> itemList = itemRepository.findAll(PageRequest.of(page, size, Sort.by("itemId").ascending()));

        /* 상품 전체 목록에서 각 상품에 리뷰 수 표시, 평점 추가 필요

        for(Item item : itemList){
            item.setReviewCount(item.getReviews().size());
        }

        */
        return itemList;
    }

    public void deleteItem(long itemId){

        Item findItem = findItem(itemId);

        /*
        관리자만 권한삭제 기능 추가 필요
         */

       itemRepository.delete(findItem);
    }
/*  관리자 검증 메서드

    public boolean isAdmin (long tokenMemberId){

        return tokenMemberId == adminId;
}
*/
    public Item verifyExistItem(long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        return item.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND));
    }
}
