package com.mainproject.be28.board.service;

import com.mainproject.be28.board.dto.BoardDto;
import com.mainproject.be28.board.entity.Board;
import com.mainproject.be28.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public Optional<Board> getBoardById(Long boardId) {
        return boardRepository.findById(boardId);
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board updateBoard(Long boardId, BoardDto updatedBoardDto) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isPresent()) {
            Board existingBoard = optionalBoard.get();

            if (updatedBoardDto.getTitle() != null) {
                existingBoard.setTitle(updatedBoardDto.getTitle());
            }
            if (updatedBoardDto.getContent() != null) {
                existingBoard.setContent(updatedBoardDto.getContent());
            }

            return boardRepository.save(existingBoard);
        } else {
            return null;
        }
    }

    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
