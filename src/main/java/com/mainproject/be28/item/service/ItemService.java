package com.mainproject.be28.item.service;

import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.item.dto.OnlyItemResponseDto;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.item.mapper.ItemMapper;
import com.mainproject.be28.item.repository.ItemRepository;
import com.mainproject.be28.item.dto.ItemSearchConditionDto;
import com.mainproject.be28.itemImage.entity.ItemImage;
import com.mainproject.be28.itemImage.repository.ItemImageRepository;
import com.mainproject.be28.itemImage.service.ItemImageService;
import com.mainproject.be28.review.entity.Review;
import com.mainproject.be28.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper mapper;
    private final ItemImageService itemImageService;
    private  final ItemImageRepository itemImageRepository;
    private final CustomBeanUtils<Item> beanUtils;

    public ItemService(ItemRepository itemRepository, ItemMapper mapper, ItemImageService itemImageService, ItemImageRepository itemImageRepository, CustomBeanUtils<Item> beanUtils) {
        this.itemRepository = itemRepository;
        this.mapper = mapper;
        this.itemImageService = itemImageService;
        this.itemImageRepository = itemImageRepository;
        this.beanUtils = beanUtils;
    }

    public Item createItem(Item item, List<MultipartFile> itemImgFileList) throws IOException{
        if(itemRepository.findItemByName(item.getName())!=null){
            throw new BusinessLogicException(ExceptionCode.ITEM_EXIST);}
        List<ItemImage> images = new ArrayList<>();
        for(int i =0 ; i < itemImgFileList.size();i++){
           MultipartFile file = itemImgFileList.get(i);
            ItemImage image = itemImageService.uploadImage(file, item);
            if(i==0){ image.setRepresentationImage("YES"); }
            else { image.setRepresentationImage("NO");}
            images.add(image);
        }
        item.setImages(images);
        itemRepository.save(item); // 저장 순서 중요.
        itemImageRepository.saveAll(images);
        return item;
    }

    public Item updateItem(Item item, List<MultipartFile> itemImgFileList) throws IOException {
        // 관리자만 수정 권한 기능 추가 필요
       Item findItem = findItem(item.getItemId());
        Item updatedItem =
                beanUtils.copyNonNullProperties(item, findItem);

        if (itemImgFileList != null) {
            List<ItemImage> images = updatedItem.getImages();
            for (MultipartFile image : itemImgFileList) {
                ItemImage img = itemImageService.uploadImage(image, item);
                images.add(img);
            }
            updatedItem.setImages(images);
        }
        return itemRepository.save(updatedItem);

    }

    public Item findItem(long itemId){

        Optional<Item> optionalItem =
                itemRepository.findById(itemId);
        Item item  = optionalItem.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND));
        item.setReviewCount(item.getReviews().size());
        item.setScore(updateScore(item));
        return item;
    }

    public Page<OnlyItemResponseDto> findItems(ItemSearchConditionDto condition){
        PageRequest pageRequest = PageRequest.of(condition.getPage()-1, condition.getSize());
        List<OnlyItemResponseDto> itemList = itemRepository.searchByCondition(condition, pageRequest);

        for(OnlyItemResponseDto onlyItemResponseDto : itemList){ // 리뷰 평균 평점, 리뷰 수
            Item item = itemRepository.findItemByName(mapper.onlyItemResponseDtotoItem(onlyItemResponseDto).getName());
            onlyItemResponseDto.setReviewCount(item.getReviews().size());
            onlyItemResponseDto.setScore(updateScore(item));
        }

        itemList = customSort(itemList, condition);

        return new PageImpl<>(itemList, pageRequest, itemList.size());
    }

    private List<OnlyItemResponseDto> customSort(List<OnlyItemResponseDto> itemList, ItemSearchConditionDto condition) {
        if (condition.getSort() != null) { // 정렬기준이 있을 경우,
            itemList.sort((item1, item2) -> sortStandard(item1, item2, condition));
        } else {
            if (condition.getOrder() != null && condition.getOrder().equals("asc")) { // 정렬 기준은 없고, 오름차순만 요구할 경우, Item ID 기준 오름차순 정렬
                itemList.sort(Comparator.comparing(OnlyItemResponseDto::getItemId));
            }
        }
        return itemList;
    }

    private int sortStandard(OnlyItemResponseDto o1, OnlyItemResponseDto o2, ItemSearchConditionDto condition){
        int result = 0;
        if(condition.getOrder()==null){condition.setOrder( "desc");}
        switch(condition.getSort()){
            case "name": result = o1.getName().compareTo(o2.getName()); break;
            case "price": result = o1.getPrice().compareTo(o2.getPrice()); break;
            case "review": result = o1.getReviewCount().compareTo(o2.getReviewCount()); break;
            case "score": result = o1.getScore().compareTo(o2.getScore()); break;
            default : break;
        }
        return condition.getOrder().equals("asc") ? result:result*-1;
    }
    public Double updateScore(Item item){
        if(item.getScore()==null){item.setScore(0.0);}
        List<Review> itemList = item.getReviews();
        double score = 0D;
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
