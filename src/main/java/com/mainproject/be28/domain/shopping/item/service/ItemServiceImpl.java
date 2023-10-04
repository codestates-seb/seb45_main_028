package com.mainproject.be28.domain.shopping.item.service;

import com.mainproject.be28.domain.shopping.item.dto.ItemDto;
import com.mainproject.be28.domain.shopping.item.dto.ItemSearchConditionDto;
import com.mainproject.be28.domain.shopping.item.dto.OnlyItemResponseDto;
import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.domain.shopping.item.entity.ItemImage;
import com.mainproject.be28.domain.shopping.item.repository.ItemRepository;
import com.mainproject.be28.domain.member.service.MemberService;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import com.mainproject.be28.domain.shopping.item.mapper.ItemMapper;
import com.mainproject.be28.domain.shopping.review.entity.Review;
import com.mainproject.be28.global.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
   private final ItemMapper mapper;
    private final MemberService memberService;
    private final ItemImageService itemImageService;
    private final CustomBeanUtils<Item> beanUtils;

    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper mapper, MemberService memberService, ItemImageService itemImageService, CustomBeanUtils<Item> beanUtils) {
        this.itemRepository = itemRepository;
        this.mapper = mapper;
        this.memberService = memberService;
        this.itemImageService = itemImageService;
        this.beanUtils = beanUtils;
    }

    /*******************public 메서드********************/
    /******일반 유저 - 상품 조회 ******/
    public Item findItem(long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND));
        setScoreReviewCount(item);
        return item;
    }

    public Page<OnlyItemResponseDto> findItems(ItemSearchConditionDto condition){
        PageRequest pageRequest = PageRequest.of(condition.getPage()-1, condition.getSize());
        List<OnlyItemResponseDto> itemList = itemRepository.searchByCondition(condition, pageRequest);

        setStatusReviewScoreURL(itemList);

        return new PageImpl<>(itemList, pageRequest, itemList.size());
    }
    /******관리자 - 상품 등록 및 수정, 삭제******/
    public Item createItem(ItemDto.Post requestBody, List<MultipartFile> itemImgFileList) throws IOException {
        memberService.verifiyAdmin();
        Item item = mapper.itemPostDtoToItem(requestBody);

        verifySameItemNameExist(item);

        List<ItemImage> images = itemImageService.saveImages(itemImgFileList, item);
        item.setImages(images);

        itemRepository.save(item); // 저장 순서 중요.
        itemImageService.saveImages(images);

        return item;
    }

    public Item updateItem(ItemDto.Patch requestBody, List<MultipartFile> itemImgFileList) throws IOException {
        memberService.verifiyAdmin();

        Item newItem = mapper.itemPatchDtoToItem(requestBody);
        Item findItem = findItem(newItem.getItemId());

        Item updatedItem =
                beanUtils.copyNonNullProperties(newItem, findItem);

        itemImageService.updateImages(itemImgFileList, findItem, updatedItem);

        updatedItem.setModifiedAt(LocalDateTime.now());

        return itemRepository.save(updatedItem);
    }

    public void deleteItem(long itemId) {
        memberService.verifiyAdmin();

        Item findItem = findItem(itemId);

        itemRepository.delete(findItem);
    }

    /********************private 메서드********************/
    private void verifySameItemNameExist(Item item) {
        if (itemRepository.findItemByName(item.getName()) != null) {
            throw new BusinessLogicException(ExceptionCode.ITEM_EXIST);
        }
    }

    private void setScoreReviewCount(Item item) {
        item.setReviewCount((long) item.getReviews().size());
        item.setScore(updateScore(item));
    }
    private void setStatusReviewScoreURL(List<OnlyItemResponseDto> itemList) {
        for(OnlyItemResponseDto onlyItemResponseDto : itemList){ // 리뷰 평균 평점, 리뷰 수
            Item item = itemRepository.findItemByName(mapper.onlyItemResponseDtotoItem(onlyItemResponseDto).getName());
            onlyItemResponseDto.setStocks(mapper.checkStock(item));
            onlyItemResponseDto.setReviewCount(item.getReviews().size());
            onlyItemResponseDto.setScore(updateScore(item));
            onlyItemResponseDto.setImageURLs(mapper.getImageResponseDto(item));
        }
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

}
