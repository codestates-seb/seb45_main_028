package com.mainproject.be28.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class BoardDto {
    private Long boardId;
    private String title;
    private Long memberId;
    private String content;
//    private LocalDateTime createdAt;
//    updatedAt 추가
//    private LocalDateTime updatedAt;
    private Long viewCount;
    private Long likeCount;
    private String boardCategory;
}