package com.mainproject.be28.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPostDto {
    private Long boardId;
    private String content;
}
