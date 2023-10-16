package com.mainproject.be28.board.dto;

import com.mainproject.be28.auditable.Auditable;
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
