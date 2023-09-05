package com.mainproject.be28.board.service;

import com.mainproject.be28.board.dto.BoardDto;
import com.mainproject.be28.board.entity.Board;
import com.mainproject.be28.board.mapper.BoardMapper;
import com.mainproject.be28.board.repository.BoardRepository;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.utils.CustomBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardMapper mapper;
    private final CustomBeanUtils<Board> beanUtils;


    public BoardService(CustomBeanUtils<Board> beanUtils) {
        this.beanUtils = beanUtils;
    }
    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public Optional<Board> getBoardById(Long boardId) {
        return boardRepository.findById(boardId);
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board updateBoard(Long boardId, Board updatedBoardDto) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        Board updatedBoard =
                    beanUtils.copyNonNullProperties(updatedBoardDto,board);
        return boardRepository.save(updatedBoard);
    }

    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
