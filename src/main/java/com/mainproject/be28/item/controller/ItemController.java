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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final static String ITEM_DEFAULT_URL = "/item";
    private final ItemService itemService;
    private final ItemMapper mapper;
    private final HttpStatus ok = HttpStatus.OK;
    @PostMapping(value = "/new"
            , consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postItem(@Valid @RequestPart ItemDto.Post requestBody
                                   , @Nullable @RequestPart(name = "images") List<MultipartFile> itemImgFileList) {

        Item itemMapper = mapper.itemPostDtoToItem(requestBody);
        Item item;
        try {
            item = itemService.createItem(itemMapper, itemImgFileList);
        } catch (BusinessLogicException e){
            throw new BusinessLogicException(ExceptionCode.ITEM_EXIST);
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.ITEM_REGIST_ERROR);
        }
        HttpStatus created = HttpStatus.CREATED;
        SingleResponseDto response = new SingleResponseDto<>(mapper.itemToItemResponseDto(item), created);

        return new ResponseEntity<>(response,created);
    }

    @PatchMapping(value = "/{item-id}", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity patchItem(@PathVariable("item-id") @Positive long itemId,
                                        @Valid @RequestPart ItemDto.Patch requestBody, @Nullable @RequestPart(name = "images") List<MultipartFile> itemImgFileList)
            throws IOException {
        requestBody.setItemId(itemId);
      Item item =itemService.updateItem(requestBody, itemImgFileList);
        SingleResponseDto response = new SingleResponseDto<>(mapper.itemToItemResponseDto(item), ok);
        return new ResponseEntity<>(new SingleResponseDto<>(response),ok);
    }

    @GetMapping("/{item-id}")
    public ResponseEntity getItem(@PathVariable("item-id") @Positive long itemId){

        Item item = itemService.findItem(itemId);

        SingleResponseDto response = new SingleResponseDto<>(mapper.itemToItemResponseDto(item), ok);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping( "/search")
    public ResponseEntity getItems(@Valid ItemSearchConditionDto itemSearchConditionDto){
        Page<OnlyItemResponseDto> items = itemService.findItems(itemSearchConditionDto);
        MultiResponseDto response = new MultiResponseDto<>(items.getContent(), items,ok);
        return new ResponseEntity<>(response, ok);
    }

    @DeleteMapping("/{item-id}")
    public ResponseEntity deleteItem(@PathVariable("item-id") @Positive long itemId){

        itemService.deleteItem(itemId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
