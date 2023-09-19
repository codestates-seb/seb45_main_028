package com.mainproject.be28.board.mapper;

import com.mainproject.be28.board.dto.BoardDto;
import com.mainproject.be28.board.dto.BoardPostDto;
import com.mainproject.be28.board.dto.BoardResponseDto;
import com.mainproject.be28.board.entity.Board;
import com.mainproject.be28.item.dto.ItemDto;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    //    public static BoardDto toDto(Board board) {
//        BoardDto dto = new BoardDto();
//        dto.setBoardId(board.getBoardId());
//        dto.setTitle(board.getTitle());
//        dto.setContent(board.getContent());
//        dto.setBoardCategory(board.getBoardCategory());
//        dto.setMemberId(board.getMember().getMemberId());
//        return dto;
//    }
    Board boardPostDtoToBoard(BoardDto boardPostDto);

    Board boardPostDtoToBoard(BoardPostDto boardPostDto);

    @Mapping(source = "memberId", target = "member.memberId")
    Board boardPatchDtoToBoard(BoardDto boardPatchDto);

    default BoardResponseDto boardToBoardResponseDto(Board board){
        if ( board == null ) {
            return null;
        }

        BoardResponseDto boardResponseDto = new BoardResponseDto();
        boardResponseDto.setTitle(board.getTitle());
        boardResponseDto.setContent(board.getContent());
        boardResponseDto.setMemberName(board.getMember().getName());
        boardResponseDto.setCreatedAt( board.getCreatedAt() );
        boardResponseDto.setModifiedAt( board.getModifiedAt() );

        return boardResponseDto;
    }
    public static Board toEntity(BoardDto dto) {
        Board board = new Board();
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        board.setBoardCategory(dto.getBoardCategory());

        Member member = new Member();
        member.setMemberId(dto.getMemberId());

        return board;
    }
}
