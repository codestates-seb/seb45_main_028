package com.mainproject.be28.item.controller;

import com.mainproject.be28.item.dto.ItemDto;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.item.mapper.ItemMapper;
import com.mainproject.be28.item.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/item")
@Validated
public class ItemController {
    private final static String ITEM_DEFAULT_URL = "/item";
    private final ItemService itemService;
//    private final MemberService memberService;  // 회원 생성 시 권한 부여 목적.
    private final ItemMapper mapper;

    public ItemController(ItemService itemService, ItemMapper mapper) {
        this.itemService = itemService;
        this.mapper = mapper;
    }

    @PostMapping("/new")
    public ResponseEntity postQuestion(@Valid @RequestBody ItemDto.Post requestBody){

//        requestBody.setMemberId(memberService.findTokenMemberId());
        Item itemMapper = mapper.itemPostDtoToItem(requestBody);

        Item item = itemService.createItem(itemMapper);

//        URI location = UriCreator.createUri(ITEM_DEFAULT_URL, item.getItemId()); // URI 전달

//        return ResponseEntity.created(location).build();
        return new ResponseEntity<>(mapper.itemToItemResponseDto(item),HttpStatus.CREATED);
    }

    @PatchMapping("/{item-id}")
    public ResponseEntity patchItem(@PathVariable("item-id") @Positive long itemId,
                                        @Valid @RequestBody ItemDto.Patch requestBody){

        requestBody.setItemId(itemId);

        Item item = mapper.itemPatchDtoToItem(requestBody);

        Item response = itemService.updateItem(item);

        return new ResponseEntity<>(mapper.itemToItemResponseDto(response),HttpStatus.OK);
    }

    @GetMapping("/{item-id}")
    public ResponseEntity getItem(@PathVariable("item-id") @Positive long itemId){

        Item response = itemService.findItem(itemId);

        ItemDto.Response itemResponse = mapper.itemToItemResponseDto(response);

        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }

//    @GetMapping  // 페이지네이션 필요. MultiResponse?
//    public ResponseEntity getItems(@Positive @RequestParam int page,
//                                       @Positive @RequestParam int size){
//
//        Page<Item> itemList = itemService.findItems(page - 1, size);
//
//        List<Item> items = itemList.getContent();
//
//        return new ResponseEntity<>(
//                new MultiResponseDto<>(mapper.questionsToOnlyQuestionResponseDtos(questions),
//                        pageQuestions), HttpStatus.OK);
//
//    }

    @DeleteMapping("/{item-id}")
    public ResponseEntity deleteQuestion(@PathVariable("item-id") @Positive long itemId){

        itemService.deleteItem(itemId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
