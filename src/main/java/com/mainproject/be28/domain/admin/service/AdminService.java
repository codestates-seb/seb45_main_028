package com.mainproject.be28.domain.admin.service;

import com.mainproject.be28.domain.community.board.dto.BoardPatchDto;
import com.mainproject.be28.domain.community.board.dto.BoardPostDto;
import com.mainproject.be28.domain.community.board.dto.BoardResponseDto;
import com.mainproject.be28.domain.shopping.item.dto.ItemDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    ItemDto.Response registItem(ItemDto.Post requestBody, List<MultipartFile> itemImgFileList) throws IOException;
    ItemDto.Response updateItem(ItemDto.Patch requestbody, List<MultipartFile> itemImgFileList) throws IOException;
    void deleteItem(long itemId);

    BoardResponseDto createBoard(BoardPostDto requestBody);
    BoardResponseDto updateBoard(BoardPatchDto requestBody);
    void deleteBoard(long boardId);

}
