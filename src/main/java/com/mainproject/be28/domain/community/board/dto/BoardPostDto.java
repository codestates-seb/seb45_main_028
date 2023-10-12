package com.mainproject.be28.domain.community.board.dto;

import com.mainproject.be28.global.auditable.Auditable;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BoardPostDto extends Auditable {
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private String boardCategory;

}
