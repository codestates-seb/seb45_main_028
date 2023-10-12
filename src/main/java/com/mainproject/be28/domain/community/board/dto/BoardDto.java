package com.mainproject.be28.domain.community.board.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Getter
@Setter
public class BoardDto {
    @NotNull
    private Long boardId;
    @NotNull
    private String title;
    @NotNull
    private Long memberId;
    private String content;

    @NotNull
    private Long viewCount;
    @NotNull
    private Long likeCount;
    @NotNull
    private String boardCategory;
}