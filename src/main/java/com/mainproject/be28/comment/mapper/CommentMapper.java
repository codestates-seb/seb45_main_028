package com.mainproject.be28.comment.mapper;

import com.mainproject.be28.comment.dto.CommentDto;
import com.mainproject.be28.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "memberId", target = "member.memberId")
    @Mapping(source = "boardId", target = "board.boardId")   // 새로 추가한 매핑
    Comment commentDtoToComment(CommentDto commentDto);

    @Mapping(source = "member.memberId", target = "memberId")
    @Mapping(source = "board.boardId", target = "boardId")
    CommentDto commentToCommentDto(Comment comment);
}