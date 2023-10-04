package com.mainproject.be28.domain.community.comment.mapper;

import com.mainproject.be28.domain.community.comment.dto.CommentPatchDto;
import com.mainproject.be28.domain.community.comment.dto.CommentPostDto;
import com.mainproject.be28.domain.community.comment.dto.CommentResponseDto;
import com.mainproject.be28.domain.community.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "boardId", target="board.boardId")
    Comment commentPostDtoToComment(CommentPostDto commentPostDto);

    @Mapping(source = "boardId", target="board.boardId")
    Comment commentPatchDtoToComment(CommentPatchDto commentPatchDto);

    @Mapping(source= "member.name", target="memberName")
    CommentResponseDto commentToCommentResponseDto(Comment comment);

}

//    @Mapping(source = "commentPostDto.content", target = "content")
////    @Mapping(target = "board", expression = "java(boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException(\"Board not found\")))")
//    Comment commentPostDtoToComment(CommentPostDto commentPostDto, Long boardId);
//}