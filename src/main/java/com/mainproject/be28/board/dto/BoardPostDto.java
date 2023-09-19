package com.mainproject.be28.board.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BoardPostDto {
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private String boardCategory;
    @NotNull
    private Long memberId;
}
