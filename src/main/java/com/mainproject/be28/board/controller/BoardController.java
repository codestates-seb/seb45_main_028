package com.mainproject.be28.board.controller;

import com.mainproject.be28.board.dto.BoardDto;
import com.mainproject.be28.board.entity.Board;
import com.mainproject.be28.board.service.BoardService;
import com.mainproject.be28.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping
    public Board createBoard(@RequestBody BoardDto boardDto) {
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setBoardCategory(boardDto.getBoardCategory());

        Member member = new Member();
        member.setMemberId(boardDto.getMemberId());
        board.setMember(member);

        return boardService.createBoard(board);
    }

    @GetMapping("/{boardId}")
    public Optional<Board> getBoardById(@PathVariable Long boardId) {
        return boardService.getBoardById(boardId);
    }

    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }

    @PutMapping("/{boardId}")
    public Board updateBoard(@PathVariable Long boardId, @RequestBody BoardDto boardDto) {
        Board updatedBoard = new Board();
        updatedBoard.setTitle(boardDto.getTitle());
        updatedBoard.setContent(boardDto.getContent());
        // ... (update other fields as needed)
        return boardService.updateBoard(boardId, updatedBoard);
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
    }
}