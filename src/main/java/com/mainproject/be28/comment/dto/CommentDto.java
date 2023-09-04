package com.mainproject.be28.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Long commentId;
    private Long boardId;
    private Long memberId;
    private String content;
    private Long likeCount;

}