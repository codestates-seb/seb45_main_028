package com.mainproject.be28.domain.community.board.controller;

import com.mainproject.be28.domain.community.board.dto.BoardResponseDto;
import com.mainproject.be28.domain.community.board.entity.Board;
import com.mainproject.be28.domain.community.board.service.BoardService;
import com.mainproject.be28.global.response.MultiResponseDto;
import com.mainproject.be28.global.response.SingleResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/{boardId}")
    public ResponseEntity getBoardById(@PathVariable("boardId") Long boardId) {
        BoardResponseDto board =boardService.getBoard(boardId);
        SingleResponseDto response = new SingleResponseDto(board, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<MultiResponseDto<BoardResponseDto>> getAllBoards(@RequestParam(name = "page", defaultValue = "1") int page,
                                                                               @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<BoardResponseDto> board = boardService.getAllBoards(page, size);
        MultiResponseDto<BoardResponseDto> response = new MultiResponseDto<>(board, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //회원아이디로 게시글 검색기능 추가
    @GetMapping("/member/{memberId}")
    public List<Board> getBoardsByMemberId(@PathVariable Long memberId) {
        return boardService.getBoardsByMemberId(memberId);
    }
    // 키워드로 검색기능 추가
    @GetMapping("/search")
    public List<Board> getBoardsByKeyword(@RequestParam String keyword){
        return boardService.getBoardsByKeyword(keyword);
    }
    // 좋아요
    @GetMapping("/sorted/likes")
    public List<Board> getAllBoardsSortedByLikes(){
        return boardService.getAllBoardSortedByLikes();
    }
    // 조회수
    @GetMapping("/sorted/views")
    public List<Board> getAllBoardsSortedByViews(){
        return boardService.getAllBoardSortedByViews();
    }
    //공지사항
    @GetMapping("/notices")
    public List<Board> getNoticeBoards(){
        return boardService.getNoticeBoards();
    }

}