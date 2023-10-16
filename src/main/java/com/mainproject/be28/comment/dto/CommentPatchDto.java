package com.mainproject.be28.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentPatchDto {
    private Long boardId;
    private Long commentId;
    private String content;
}
