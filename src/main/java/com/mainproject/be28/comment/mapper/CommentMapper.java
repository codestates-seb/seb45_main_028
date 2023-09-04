package com.mainproject.be28.comment.mapper;

import com.mainproject.be28.comment.dto.CommentDto;
import com.mainproject.be28.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "memberId", target = "member.memberId")
    Comment commentDtoToComment(CommentDto commentDto);

    @Mapping(source = "member.memberId", target = "memberId")
    CommentDto commentToCommentDto(Comment comment);
}