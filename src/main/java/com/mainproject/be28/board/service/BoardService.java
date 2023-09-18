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
        if (updatedBoardDto.getTitle() != null) {
            board.setTitle(updatedBoardDto.getTitle());
        }
        if (updatedBoardDto.getContent() != null) {
            board.setContent(updatedBoardDto.getContent());
        }

        return boardRepository.save(board);
    }
    // 회원아이디로 게시글 검색기능 추가
    public List<Board> getBoardsByMemberId(Long memberId) {
        return boardRepository.findByMember_MemberId(memberId);
    }
    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }
// 키워드로 게시글검색기능 추가
    public List<Board> getBoardsByKeyword(String keyword){
        return boardRepository.findByTitleContainingOrContentContaining(keyword, keyword);
    }
// 좋아요순 내림차순정렬
    public List<Board> getAllBoardSortedByLikes(){
        return boardRepository.findAllByOrderByLikeCountDesc();
    }
// 조회수순 내림차순정렬
    public List<Board> getAllBoardSortedByViews(){
        return boardRepository.findAllByOrderByViewCountDesc();
    }
    //공지사항
    public List<Board> getNoticeBoards() {
        return boardRepository.findByBoardCategoryOrderByCreatedAtDesc("공지사항");
    }

}
