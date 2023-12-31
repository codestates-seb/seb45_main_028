package com.mainproject.be28.board.service;

import com.mainproject.be28.board.dto.BoardPostDto;
import com.mainproject.be28.board.dto.BoardResponseDto;
import com.mainproject.be28.board.entity.Board;
import com.mainproject.be28.board.mapper.BoardMapper;
import com.mainproject.be28.board.repository.BoardRepository;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.member.service.MemberService;
import com.mainproject.be28.utils.CustomBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardMapper mapper;
    @Autowired
    private MemberService memberService;
    private final CustomBeanUtils<Board> beanUtils;

    public BoardService(CustomBeanUtils<Board> beanUtils) {
        this.beanUtils = beanUtils;
    }
    public BoardResponseDto createBoard(BoardPostDto boardPostDto) {
        memberService.verifiyAdmin();
        Board board = mapper.boardPostDtoToBoard(boardPostDto);
        board.setMember(memberService.findTokenMember());
        boardRepository.save(board);
        return mapper.boardToBoardResponseDto(board);
    }

    public BoardResponseDto getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        board.setViewCount(board.getViewCount()+1);
        boardRepository.save(board);
        return  mapper.boardToBoardResponseDto(board);
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }


    public BoardResponseDto updateBoard(Long boardId, Board updatedBoardDto) {
        memberService.verifiyAdmin();
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        if (updatedBoardDto.getTitle() != null) {
            board.setTitle(updatedBoardDto.getTitle());
        }
        if (updatedBoardDto.getContent() != null) {
            board.setContent(updatedBoardDto.getContent());
        }
        board.setModifiedAt(LocalDateTime.now());
        board = boardRepository.save(board);

        BoardResponseDto response = mapper.boardToBoardResponseDto(board);
        return response;
    }
    // 회원아이디로 게시글 검색기능 추가
    public List<Board> getBoardsByMemberId(Long memberId) {
        return boardRepository.findByMember_MemberId(memberId);
    }
    public void deleteBoard(Long boardId) {
        memberService.verifiyAdmin();
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
