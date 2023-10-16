package com.mainproject.be28.board.controller;

import com.mainproject.be28.board.dto.BoardDto;
import com.mainproject.be28.board.dto.BoardPostDto;
import com.mainproject.be28.board.dto.BoardResponseDto;
import com.mainproject.be28.board.entity.Board;
import com.mainproject.be28.board.mapper.BoardMapper;
import com.mainproject.be28.board.service.BoardService;
import com.mainproject.be28.response.SingleResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardMapper mapper;
    @PostMapping
    public ResponseEntity createBoard(@RequestBody BoardPostDto boardPostDto) {
        BoardResponseDto board = boardService.createBoard(boardPostDto);
        SingleResponseDto response = new SingleResponseDto(board, HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/{boardId}")
    public ResponseEntity getBoardById(@PathVariable("boardId") Long boardId) {
        BoardResponseDto board =boardService.getBoardById(boardId);
        SingleResponseDto response = new SingleResponseDto(board, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
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
    @PatchMapping("/{boardId}")
    public ResponseEntity updateBoard(@PathVariable("boardId") Long boardId, @RequestBody BoardDto boardDto) {
        boardDto.setBoardId(boardId);
        Board board = mapper.boardPatchDtoToBoard(boardDto);

        BoardResponseDto boardResponseDto = boardService.updateBoard(boardId, board);
        SingleResponseDto response = new SingleResponseDto(boardResponseDto, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable("boardId") Long boardId) {
        boardService.deleteBoard(boardId);
    }
}