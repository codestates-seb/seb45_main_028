package com.mainproject.be28.domain.community.board.service;

import com.mainproject.be28.domain.community.board.dto.BoardPatchDto;
import com.mainproject.be28.domain.community.board.dto.BoardPostDto;
import com.mainproject.be28.domain.community.board.dto.BoardResponseDto;
import com.mainproject.be28.domain.community.board.entity.Board;
import com.mainproject.be28.domain.community.board.mapper.BoardMapper;
import com.mainproject.be28.domain.community.board.repository.BoardRepository;
import com.mainproject.be28.domain.member.service.Layer2.MemberVerifyService;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import com.mainproject.be28.global.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardMapper mapper;
    private final MemberVerifyService memberVerifyService;
    private final CustomBeanUtils<Board> beanUtils;

    public BoardService(BoardRepository boardRepository, BoardMapper mapper, MemberVerifyService memberVerifyService, CustomBeanUtils<Board> beanUtils) {
        this.boardRepository = boardRepository;
        this.mapper = mapper;
        this.memberVerifyService = memberVerifyService;
        this.beanUtils = beanUtils;
    }

    public BoardResponseDto createBoard(BoardPostDto boardPostDto) {
        Board board = mapper.boardPostDtoToBoard(boardPostDto);
        board.setMember(memberVerifyService.findTokenMember());
       board =  boardRepository.save(board);
        return mapper.boardToBoardResponseDto(board);
    }

    public BoardResponseDto getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        board.setViewCount(board.getViewCount()+1);
        boardRepository.save(board);
        return  mapper.boardToBoardResponseDto(board);
    }

    public Page<BoardResponseDto> getAllBoards(int page, int size) {
        List<Board> boardList =boardRepository.findAll();
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        for(Board board : boardList){
            boardResponseDtoList.add(mapper.boardToBoardResponseDto(board));
        }
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return new PageImpl<>(boardResponseDtoList, pageRequest, boardResponseDtoList.size());
    }


    public BoardResponseDto updateBoard(BoardPatchDto updatedBoardDto) {
        Board findBoard = verifyExistBoard(updatedBoardDto.getBoardId());
        Board newBoard = mapper.boardPatchDtoToBoard(updatedBoardDto);

        Board updateBoard  =beanUtils.copyNonNullProperties(newBoard, findBoard);
        updateBoard.setModifiedAt(LocalDateTime.now());
        boardRepository.save(updateBoard);

        return mapper.boardToBoardResponseDto(updateBoard);
    }

    private Board verifyExistBoard(long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        return optionalBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
    }

    // 회원아이디로 게시글 검색기능 추가
    public List<Board> getBoardsByMemberId(Long memberId) {
        return boardRepository.findByMember_MemberId(memberId);
    }
    public void deleteBoard(Long boardId) {
        Board board = verifyExistBoard(boardId);
        boardRepository.delete(board);
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
