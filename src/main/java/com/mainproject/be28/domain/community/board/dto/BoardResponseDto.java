package com.mainproject.be28.domain.community.board.dto;

import com.mainproject.be28.global.auditable.Auditable;
import com.mainproject.be28.domain.community.comment.dto.CommentResponseDto;
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
