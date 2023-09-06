package com.mainproject.be28.item.service;

import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.item.dto.OnlyItemResponseDto;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.item.mapper.ItemMapper;
import com.mainproject.be28.item.repository.ItemRepository;
import com.mainproject.be28.item.repository.ItemSearchCondition;
import com.mainproject.be28.itemImage.entity.ItemImage;
import com.mainproject.be28.itemImage.service.ItemImageService;
import com.mainproject.be28.review.entity.Review;
import com.mainproject.be28.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper mapper;
    private final ItemImageService itemImageService;
    private final CustomBeanUtils<Item> beanUtils;
    public ItemService(ItemRepository itemRepository, ItemMapper mapper, ItemImageService itemImageService, CustomBeanUtils<Item> beanUtils) {
        this.itemRepository = itemRepository;
        this.mapper = mapper;
        this.itemImageService = itemImageService;
        this.beanUtils = beanUtils;
    }


    public Item createItem(Item item){
//            , List<MultipartFile> itemImgFileList) throws Exception{
        if(itemRepository.findItemByName(item.getName())!=null){
            throw new BusinessLogicException(ExceptionCode.ITEM_EXIST);}
       item = itemRepository.save(item);

//        for (int i = 0; i < itemImgFileList.size(); i++) {
//            ItemImage itemimg = new ItemImage();
//            itemimg.setItem(item);
//            if (i == 0) {
//                itemimg.setRepresentationImage("Yes");
//            } else{
//                itemimg.setRepresentationImage("No");
//            }
//            itemImageService.saveItemImg(itemimg, itemImgFileList.get(i));
//        }
        return item;
    }

    public Item updateItem(Item item) {
       Item findItem = findItem(item.getItemId());

        // 관리자만 수정 권한 기능 추가 필요

         Item updatedItem =
                    beanUtils.copyNonNullProperties(item, findItem);
            return itemRepository.save(updatedItem);

    }

    public Item findItem(long itemId){

        Optional<Item> optionalItem =
                itemRepository.findById(itemId);
        Item item  = optionalItem.get();
        item.setReviewCount(item.getReviews().size());
        item.setScore(updateScore(item));
        return item;
    }

    public Page<OnlyItemResponseDto> findItems(ItemSearchCondition condition, int page, int size,String sort){
        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.by(sort).descending());
        System.out.println(pageRequest.toString());
        Page<OnlyItemResponseDto> itemList = itemRepository.searchByCondition(condition, pageRequest);

        for(OnlyItemResponseDto onlyItemResponseDto : itemList){ //
            Item item = itemRepository.findItemByName(mapper.onlyItemResponseDtotoItem(onlyItemResponseDto).getName());
            onlyItemResponseDto.setReviewCount(item.getReviews().size());
            onlyItemResponseDto.setScore(updateScore(item));
        }
        return itemList;
    }
    public Double updateScore(Item item){
        if(item.getScore()==null){item.setScore(0.0);}
        Double score = 0D;
        List<Review> itemList = item.getReviews();
        for (Review review : itemList) {
            score += review.getScore();
        }
        score /= item.getReviews().size();
        score = (double)Math.round(score*100)/100;
        return score;
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
}
