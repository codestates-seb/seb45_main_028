package com.mainproject.be28.item.service;

import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.item.dto.ItemDto;
import com.mainproject.be28.item.dto.ItemSearchConditionDto;
import com.mainproject.be28.item.dto.OnlyItemResponseDto;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.item.mapper.ItemMapper;
import com.mainproject.be28.item.repository.ItemRepository;
import com.mainproject.be28.itemImage.entity.ItemImage;
import com.mainproject.be28.itemImage.repository.ItemImageRepository;
import com.mainproject.be28.itemImage.service.ItemImageService;
import com.mainproject.be28.member.service.MemberService;
import com.mainproject.be28.review.entity.Review;
import com.mainproject.be28.utils.CustomBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper mapper;
    private final ItemImageService itemImageService;
    private  final ItemImageRepository itemImageRepository;
    private final CustomBeanUtils<Item> beanUtils;
    private final MemberService memberService;

    public ItemService(ItemRepository itemRepository, ItemMapper mapper, ItemImageService itemImageService, ItemImageRepository itemImageRepository, CustomBeanUtils<Item> beanUtils, MemberService memberService) {
        this.itemRepository = itemRepository;
        this.mapper = mapper;
        this.itemImageService = itemImageService;
        this.itemImageRepository = itemImageRepository;
        this.beanUtils = beanUtils;
        this.memberService = memberService;
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

        List<ItemImage> images = saveImages(itemImgFileList, item);
        item.setImages(images);

        itemRepository.save(item); // 저장 순서 중요.
        if(images.size()>0){itemImageRepository.saveAll(images);}

        return item;
    }

    public Item updateItem(ItemDto.Patch requestBody, List<MultipartFile> itemImgFileList) throws IOException {
        memberService.verifiyAdmin();

        Item newItem = mapper.itemPatchDtoToItem(requestBody);
        Item findItem = findItem(newItem.getItemId());

        Item updatedItem =
                beanUtils.copyNonNullProperties(newItem, findItem);

//        if(findItem.getStock()>0&&newItem.getStock()==0){updatedItem.setStock(findItem.getStock());}
        updateImages(itemImgFileList, findItem, updatedItem);

        updatedItem.setModifiedAt(LocalDateTime.now());

        return itemRepository.save(updatedItem);
    }

    public void deleteItem(long itemId){
        memberService.verifiyAdmin();

        Item findItem = findItem(itemId);

        itemImageService.deleteImage(findItem);
        itemRepository.delete(findItem);
    }

    /********************private 메서드********************/

    private List<ItemImage> saveImages(List<MultipartFile> itemImgFileList, Item item) throws IOException {
        List<ItemImage> images = new ArrayList<>();
        if (itemImgFileList != null){
            images = saveImage(item, itemImgFileList);
        }
        return images;
    }

    private void verifySameItemNameExist(Item item) {
        if (itemRepository.findItemByName(item.getName()) != null) {
            throw new BusinessLogicException(ExceptionCode.ITEM_EXIST);
        }
    }

    private void updateImages(List<MultipartFile> itemImgFileList, Item findItem, Item updatedItem) throws IOException {
        List<ItemImage> images = findItem.getImages()==null?new ArrayList<>():new ArrayList<>(findItem.getImages());

        //새로운 파일 이미지가 있다면, 새로 추가
        if (itemImgFileList != null) {
            for (MultipartFile image : itemImgFileList) {
                ItemImage img = itemImageService.uploadImage(image, updatedItem);
                images.add(img);
            }
            //대표 이미지 설정이 안되어있다면, 맨 첫번째 이미지 대표이미지로 설정
            ItemImage repImg = images.get(0);
            if(repImg.getRepresentationImage()==null||!repImg.getRepresentationImage().equals("YES")) {
                repImg.setRepresentationImage("YES");
                images.set(0, repImg);
            }
            updatedItem.setImages(images);
            itemImageRepository.saveAll(images);
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
    private List<ItemImage> saveImage(Item item,List<MultipartFile> itemImgFileList ) throws IOException {
        List<ItemImage> images = new ArrayList<>();
        for (int i = 0; i < itemImgFileList.size(); i++) {
            MultipartFile file = itemImgFileList.get(i);
            ItemImage image = itemImageService.uploadImage(file, item);
            if (i == 0) {
                image.setRepresentationImage("YES");
            } else {
                image.setRepresentationImage("NO");
            }
            images.add(image);
        }
        return images;
    }
}
