package com.mainproject.be28.domain.shopping.item.controller;

import com.mainproject.be28.domain.shopping.item.dto.ItemDto;
import com.mainproject.be28.domain.shopping.item.dto.ItemSearchConditionDto;
import com.mainproject.be28.domain.shopping.item.dto.OnlyItemResponseDto;
import com.mainproject.be28.domain.shopping.service.ShoppingService;
import com.mainproject.be28.global.response.MultiResponseDto;
import com.mainproject.be28.global.response.SingleResponseDto;
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
    private final ShoppingService shoppingService;
    private final HttpStatus ok = HttpStatus.OK;
    @GetMapping("/{item-id}")
    public ResponseEntity<SingleResponseDto<ItemDto.Response>> getItem(@PathVariable("item-id") @Positive long itemId){
        ItemDto.Response item = shoppingService.findItem(itemId);
        SingleResponseDto<ItemDto.Response> response = new SingleResponseDto<>(item, ok);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping( "/search")
    public ResponseEntity<MultiResponseDto<OnlyItemResponseDto>> getItems(@Valid ItemSearchConditionDto itemSearchConditionDto){

        Page<OnlyItemResponseDto> items = shoppingService.findItems(itemSearchConditionDto);

        MultiResponseDto<OnlyItemResponseDto> response = new MultiResponseDto<>(items,ok);
        return new ResponseEntity<>(response, ok);
    }



}
