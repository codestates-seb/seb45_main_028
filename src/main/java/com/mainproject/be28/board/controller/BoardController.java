package com.mainproject.be28.board.controller;

import com.mainproject.be28.board.dto.BoardDto;
import com.mainproject.be28.board.entity.Board;
import com.mainproject.be28.board.mapper.BoardMapper;
import com.mainproject.be28.board.service.BoardService;
import com.mainproject.be28.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardMapper mapper;
    @PostMapping
    public ResponseEntity createBoard(@RequestBody BoardDto boardDto) {
        Board mapperBoard = mapper.boardPostDtoToBoard(boardDto);

        return new ResponseEntity<>(boardService.createBoard(mapperBoard), HttpStatus.CREATED);
//        Board board = new Board();
//        board.setTitle(boardDto.getTitle());
//        board.setContent(boardDto.getContent());
//        board.setBoardCategory(boardDto.getBoardCategory());
//
//        Member member = new Member();
//        member.setMemberId(boardDto.getMemberId());
//        //board.setMember(member);
//
//        return boardService.createBoard(board);
    }

    @GetMapping("/{boardId}")
    public Optional<Board> getBoardById(@PathVariable("boardId") Long boardId) {
        return boardService.getBoardById(boardId);
    }

    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }

    @PatchMapping("/{boardId}")
    public Board updateBoard(@PathVariable("boardId") Long boardId, @RequestBody BoardDto boardDto) {
        boardDto.setBoardId(boardId);
        Board board = mapper.boardPatchDtoToBoard(boardDto);
        return boardService.updateBoard(boardId, board);
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable("boardId") Long boardId) {
        boardService.deleteBoard(boardId);
    }
}