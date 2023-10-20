package com.mainproject.be28.domain.shopping.item.service;

import com.mainproject.be28.domain.shopping.item.dto.ItemDto;
import com.mainproject.be28.domain.shopping.item.dto.ItemSearchConditionDto;
import com.mainproject.be28.domain.shopping.item.dto.OnlyItemResponseDto;
import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.domain.shopping.item.entity.ItemImage;
import com.mainproject.be28.domain.shopping.item.repository.ItemRepository;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import com.mainproject.be28.domain.shopping.item.mapper.ItemMapper;
import com.mainproject.be28.domain.shopping.review.entity.Review;
import com.mainproject.be28.global.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
   private final ItemMapper mapper;
    private final ItemImageService itemImageService;

    /*  일반 유저 - 상품 조회 */
    public ItemDto.Response findItem(long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND));
        setScoreReviewCount(item);
        return mapper.itemToItemResponseDto(item);
    }


    public Page<OnlyItemResponseDto> findItems(ItemSearchConditionDto condition){
        setDefaultPageRequest(condition);
        PageRequest pageRequest = PageRequest.of(condition.getPage()-1, condition.getSize());
        List<Item> itemList = itemRepository.searchByCondition(condition, pageRequest);
        for (Item item : itemList) {
            setScoreReviewCount(item);
        }

        List<OnlyItemResponseDto>  items = mapper.itemListToOnlyItemResponseDtoList(itemList);

        return new PageImpl<>(items, pageRequest,items.size());
    }
    /* 관리자 - 상품 등록 및 수정, 삭제 */
    @Transactional
    public ItemDto.Response createItem(ItemDto.Post requestBody, List<MultipartFile> itemImgFileList) throws IOException {

        Item item = mapper.itemPostDtoToItem(requestBody);
        verifySameItemNameExist(item);
        itemRepository.save(item); // 상품 저장 후, 상품에 매핑된 이미지 저장하는 순서.

        createItemImage(itemImgFileList, item);

        return mapper.itemToItemResponseDto(item);
    }
    @Transactional
    public ItemDto.Response updateItem(ItemDto.Patch requestBody, List<MultipartFile> itemImgFileList) throws IOException {
        Item newItem = mapper.itemPatchDtoToItem(requestBody);
        Item findItem = verifyExistItem(newItem.getItemId());

        CustomBeanUtils<Item> beanUtils =  new CustomBeanUtils<>();
        Item updatedItem =
                beanUtils.copyNonNullProperties(newItem, findItem);

        itemImageService.updateImages(itemImgFileList, findItem, updatedItem);

        updatedItem.setModifiedAt(LocalDateTime.now());

        return mapper.itemToItemResponseDto(itemRepository.save(updatedItem));
    }

    @Override
    public void deleteItem(long id) {
        Item findItem = verifyExistItem(id);
        itemRepository.delete(findItem);
    }


    /* Item Service 용 메서드 */

    public Item verifyExistItem(long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND));
        setScoreReviewCount(item);
        return item;
    }
    public void save(Item item) {
        itemRepository.save(item);
    }

    /* private 메서드 */
    private void verifySameItemNameExist(Item item) {
        if (itemRepository.findItemByName(item.getName()) != null) {
            throw new BusinessLogicException(ExceptionCode.ITEM_EXIST);
        }
    }

    private void createItemImage(List<MultipartFile> itemImgFileList, Item item) throws IOException {
        List<ItemImage> images = itemImageService.saveImages(itemImgFileList, item);
        item.setImages(images);
        itemImageService.saveImages(images);
    }

    private void setScoreReviewCount(Item item) {
        item.setReviewCount((item.getReviews().size()));
        item.setScore(updateScore(item));
    }

    private Double updateScore(Item item){
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

    private void setDefaultPageRequest(ItemSearchConditionDto condition) {
        if(condition.getPage()==0){
            condition.setPage(1);}
        if(condition.getSize()==0){
            condition.setSize(9);}
    }
    
}
