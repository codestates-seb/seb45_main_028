package com.mainproject.be28.domain.community.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPostDto {
    private Long boardId;
    private String content;
}
