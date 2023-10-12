package com.mainproject.be28.domain.community.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentPatchDto {
    private Long boardId;
    private Long commentId;
    private String content;
}
