package com.mainproject.be28.domain.admin.service;

import com.mainproject.be28.domain.community.board.dto.BoardPatchDto;
import com.mainproject.be28.domain.community.board.dto.BoardPostDto;
import com.mainproject.be28.domain.community.board.dto.BoardResponseDto;
import com.mainproject.be28.domain.community.board.service.BoardService;
import com.mainproject.be28.domain.shopping.item.dto.ItemDto;
import com.mainproject.be28.domain.shopping.item.service.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    private final BoardService boardService;
    private final ItemService itemService;

    public AdminServiceImpl(BoardService boardService, ItemService itemService) {
        this.boardService = boardService;
        this.itemService = itemService;
    }

    @Override
    public ItemDto.Response registItem(ItemDto.Post requestBody, List<MultipartFile> itemImgFileList) throws IOException {
        return itemService.createItem(requestBody, itemImgFileList);
    }

    @Override
    public ItemDto.Response updateItem(ItemDto.Patch requestBody, List<MultipartFile> itemImgFileList) throws IOException {
        return itemService.updateItem(requestBody,itemImgFileList);
    }

    @Override
    public void deleteItem(long itemId) {
        itemService.deleteItem(itemId);
    }

    @Override
    public BoardResponseDto createBoard(BoardPostDto requestBody) {
        return boardService.createBoard(requestBody);
    }

    @Override
    public BoardResponseDto updateBoard(BoardPatchDto requestBody) {
        return boardService.updateBoard(requestBody);
    }

    @Override
    public void deleteBoard(long boardId) {
        boardService.deleteBoard(boardId);
    }
}
