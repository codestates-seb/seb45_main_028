package com.mainproject.be28.domain.community.comment.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentDto {
    @NotNull
    private Long commentId;
//    @NotNull
//    private Long boardId;
    @NotNull
    private Long memberId;
    private String content;
    @NotNull
    private Long likeCount;

}