package com.mainproject.be28.board.mapper;

import com.mainproject.be28.board.dto.BoardDto;
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
    @Mapping(source = "memberId", target = "member.memberId")
    Board boardPostDtoToBoard(BoardDto boardPostDto);
    @Mapping(source = "memberId", target = "member.memberId")
    Board boardPatchDtoToBoard(BoardDto boardPatchDto);

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
