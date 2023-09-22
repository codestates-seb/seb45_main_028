package com.mainproject.be28.board.dto;

import com.mainproject.be28.auditable.Auditable;
import com.mainproject.be28.comment.dto.CommentResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class BoardResponseDto extends Auditable {
    private String memberName;
    private String title;
    private String content;
    private long viewCount;
    private List<CommentResponseDto> comments;
}
