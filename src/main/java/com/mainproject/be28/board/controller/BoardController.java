package com.mainproject.be28.board.controller;

import com.mainproject.be28.board.dto.BoardDto;
import com.mainproject.be28.board.entity.Board;
import com.mainproject.be28.board.mapper.BoardMapper;
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
    @Autowired
    private BoardMapper mapper;
    @PostMapping
    public Board createBoard(@RequestBody BoardDto boardDto) {
        /*
        // 매퍼에서 이미 했음.
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setBoardCategory(boardDto.getBoardCategory());

        // 매퍼에서 멤버를 매핑.
        Member member = new Member();
        member.setMemberId(boardDto.getMemberId());
        board.setMember(member);
*/
        Board mapperBoard = mapper.boardPostDtoToBoard(boardDto);

        return boardService.createBoard(mapperBoard);
    }

    @GetMapping("/{boardId}")
    public Optional<Board> getBoardById(@PathVariable("boardId") Long boardId) {
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
    public void deleteBoard(@PathVariable("boardId") Long boardId) {
        boardService.deleteBoard(boardId);
    }
}