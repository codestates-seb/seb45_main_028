package com.mainproject.be28.domain.shopping.item.controller;

import com.mainproject.be28.domain.shopping.item.dto.ItemDto;
import com.mainproject.be28.domain.shopping.item.dto.ItemSearchConditionDto;
import com.mainproject.be28.domain.shopping.item.dto.OnlyItemResponseDto;
import com.mainproject.be28.domain.shopping.item.service.ItemService;
import com.mainproject.be28.global.response.MultiResponseDto;
import com.mainproject.be28.global.response.SingleResponseDto;
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
    private final ItemService itemService;
    private final HttpStatus ok = HttpStatus.OK;

    @PostMapping(value = "/new"
            , consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SingleResponseDto<ItemDto.Response>> createItem(@Valid @RequestPart ItemDto.Post requestBody
                                   , @Nullable @RequestPart(name = "images") List<MultipartFile> itemImgFileList) throws IOException{

        ItemDto.Response item =  itemService.createItem(requestBody, itemImgFileList);

        HttpStatus created = HttpStatus.CREATED;

        SingleResponseDto<ItemDto.Response> response = new SingleResponseDto<>(item, created);

        return new ResponseEntity<>(response, created);
    }

    @PatchMapping(value = "/{item-id}", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SingleResponseDto<ItemDto.Response>> updateItem(@PathVariable("item-id") @Positive long itemId,
                                        @Valid @RequestPart ItemDto.Patch requestBody, @Nullable @RequestPart(name = "images") List<MultipartFile> itemImgFileList)
            throws IOException {

        requestBody.setItemId(itemId);
        ItemDto.Response itemResponse =  itemService.updateItem(requestBody, itemImgFileList);

        SingleResponseDto<ItemDto.Response> response = new SingleResponseDto<>(itemResponse, ok);
        return new ResponseEntity<>(response,ok);
    }

    @GetMapping("/{item-id}")
    public ResponseEntity<SingleResponseDto<ItemDto.Response>> getItem(@PathVariable("item-id") @Positive long itemId){

        ItemDto.Response item = itemService.findItem(itemId);

        SingleResponseDto<ItemDto.Response> response = new SingleResponseDto<>(item, ok);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping( "/search")
    public ResponseEntity<MultiResponseDto<OnlyItemResponseDto>> getItems(@Valid ItemSearchConditionDto itemSearchConditionDto){
        Page<OnlyItemResponseDto> items = itemService.findItems(itemSearchConditionDto);

        MultiResponseDto<OnlyItemResponseDto> response = new MultiResponseDto<>(items.getContent(), items,ok);
        return new ResponseEntity<>(response, ok);
    }

    @DeleteMapping("/{item-id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable("item-id") @Positive long itemId){

        itemService.deleteItem(itemId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
