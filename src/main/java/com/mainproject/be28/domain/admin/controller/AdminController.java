package com.mainproject.be28.domain.admin.controller;

import com.mainproject.be28.domain.admin.service.AdminService;
import com.mainproject.be28.domain.community.board.dto.BoardPatchDto;
import com.mainproject.be28.domain.community.board.dto.BoardPostDto;
import com.mainproject.be28.domain.community.board.dto.BoardResponseDto;
import com.mainproject.be28.domain.shopping.item.dto.ItemDto;
import com.mainproject.be28.global.response.SingleResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("admin")
public class AdminController {
    private final AdminService adminService;
    private final HttpStatus ok = HttpStatus.OK;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @PostMapping(value = "/item/register"
            , consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SingleResponseDto<ItemDto.Response>> createItem(@Valid @RequestPart ItemDto.Post requestBody
            , @Nullable @RequestPart(name = "images") List<MultipartFile> itemImgFileList) throws IOException {

        ItemDto.Response item =  adminService.registItem(requestBody, itemImgFileList);

        HttpStatus created = HttpStatus.CREATED;

        SingleResponseDto<ItemDto.Response> response = new SingleResponseDto<>(item, created);

        return new ResponseEntity<>(response, created);
    }

    @PatchMapping(value = "/item/update/{item-id}", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SingleResponseDto<ItemDto.Response>> updateItem(@PathVariable("item-id") @Positive long itemId,
                                                                          @Valid @RequestPart ItemDto.Patch requestBody, @Nullable @RequestPart(name = "images") List<MultipartFile> itemImgFileList)
            throws IOException {

        requestBody.setItemId(itemId);
        ItemDto.Response itemResponse =  adminService.updateItem(requestBody, itemImgFileList);

        SingleResponseDto<ItemDto.Response> response = new SingleResponseDto<>(itemResponse, ok);
        return new ResponseEntity<>(response,ok);
    }

    @DeleteMapping("/item/delete/{item-id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable("item-id") @Positive long itemId){

        adminService.deleteItem(itemId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/board/register")
    public ResponseEntity<SingleResponseDto<BoardResponseDto>> createBoard(@RequestBody BoardPostDto boardPostDto) {
        BoardResponseDto board = adminService.createBoard(boardPostDto);
        SingleResponseDto<BoardResponseDto> response = new SingleResponseDto<>(board, HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/board/update/{board-Id}")
    public ResponseEntity<SingleResponseDto<BoardResponseDto>> updateBoard(@PathVariable("board-Id") Long boardId, @RequestBody BoardPatchDto boardPatchDto) {
        boardPatchDto.setBoardId(boardId);
        BoardResponseDto boardResponseDto = adminService.updateBoard(boardPatchDto);
        SingleResponseDto<BoardResponseDto> response = new SingleResponseDto<>(boardResponseDto, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/board/delete/{board-Id}")
    public void deleteBoard(@PathVariable("board-Id") Long boardId) {
        adminService.deleteBoard(boardId);
    }

}
