package com.mainproject.be28.item.controller;

import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.item.dto.ItemDto;
import com.mainproject.be28.item.dto.OnlyItemResponseDto;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.item.mapper.ItemMapper;
import com.mainproject.be28.item.dto.ItemSearchConditionDto;
import com.mainproject.be28.item.service.ItemService;
import com.mainproject.be28.response.MultiResponseDto;
import com.mainproject.be28.response.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final static String ITEM_DEFAULT_URL = "/item";
    private final ItemService itemService;
//    private final MemberService memberService;  // 회원 생성 시 권한 부여 목적.
    private final ItemMapper mapper;

    @PostMapping("/new")
    public ResponseEntity postItem(@Valid @RequestBody ItemDto.Post requestBody){
//                                   ,@RequestParam(name = "itemImgFile") List<MultipartFile> itemImgFileList) {

//        requestBody.setMemberId(memberService.findTokenMemberId());
        Item itemMapper = mapper.itemPostDtoToItem(requestBody);
    Item item;
        try {
            item = itemService.createItem(itemMapper);
//              상품 이미지 등록 보류      , itemImgFileList);
        }
        catch (Exception e){
            throw new BusinessLogicException(ExceptionCode.ITEM_REGIST_ERROR);
        }
//        URI location = UriCreator.createUri(ITEM_DEFAULT_URL, item.getItemId()); // URI 전달

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.itemToItemResponseDto(item)),HttpStatus.CREATED);
    }

    @PatchMapping("/{item-id}")
    public ResponseEntity patchItem(@PathVariable("item-id") @Positive long itemId,
                                        @Valid @RequestBody ItemDto.Patch requestBody){
        requestBody.setItemId(itemId);
        Item item = mapper.itemPatchDtoToItem(requestBody);
        Item response = itemService.updateItem(item);

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.itemToItemResponseDto(response)),HttpStatus.OK);
    }

    @GetMapping("/{item-id}")
    public ResponseEntity getItem(@PathVariable("item-id") @Positive long itemId){

        Item response = itemService.findItem(itemId);

        ItemDto.Response itemResponse = mapper.itemToItemResponseDto(response);

        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity getItems(@Valid @RequestBody ItemSearchConditionDto itemSearchConditionDto){
        Page<OnlyItemResponseDto> items = itemService.findItems(itemSearchConditionDto);
        return new ResponseEntity<>( new SingleResponseDto<>(items), HttpStatus.OK);
    }

    @DeleteMapping("/{item-id}")
    public ResponseEntity deleteItem(@PathVariable("item-id") @Positive long itemId){

        itemService.deleteItem(itemId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
