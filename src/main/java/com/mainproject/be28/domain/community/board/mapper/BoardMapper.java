package com.mainproject.be28.domain.community.board.mapper;

import com.mainproject.be28.domain.community.board.dto.BoardPatchDto;
import com.mainproject.be28.domain.community.board.dto.BoardPostDto;
import com.mainproject.be28.domain.community.board.dto.BoardResponseDto;
import com.mainproject.be28.domain.community.board.entity.Board;
import com.mainproject.be28.domain.community.comment.dto.CommentResponseDto;
import com.mainproject.be28.domain.community.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

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

    Board boardPostDtoToBoard(BoardPostDto boardPostDto);

    @Mapping(source = "memberId", target = "member.memberId")
    Board boardPatchDtoToBoard(BoardPatchDto boardPatchDto);

    Board boardResponseDtoTOBoard(BoardResponseDto boardResponseDto);
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
        boardResponseDto.setViewCount(board.getViewCount());
        boardResponseDto.setComments(getCommentResponseDtoList(board));
        return boardResponseDto;
    }

    default List<CommentResponseDto> getCommentResponseDtoList(Board board) {
        List<Comment> commentList = board.getComments();
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        if(commentList != null) {
            for (Comment comment : commentList) {
                CommentResponseDto commentResponseDto = new CommentResponseDto();
                commentResponseDto.setMemberName(comment.getMember().getName());
                commentResponseDto.setContent(comment.getContent());
                commentResponseDto.setCreatedAt(comment.getCreatedAt());
                commentResponseDto.setModifiedAt(comment.getModifiedAt());
                commentResponseDto.setLikeCount(comment.getLikeCount());
                commentResponseDtos.add(commentResponseDto);
            }
        }
        return commentResponseDtos;
    }

}
