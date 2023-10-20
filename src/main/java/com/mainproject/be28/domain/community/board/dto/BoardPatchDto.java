package com.mainproject.be28.domain.community.board.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BoardPatchDto {
    @NotNull
    private Long boardId;
    @NotNull
    private Long memberId;
    private String title;

    private String content;
    private String boardCategory;
}