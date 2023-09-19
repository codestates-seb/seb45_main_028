package com.mainproject.be28.board.dto;

import com.mainproject.be28.auditable.Auditable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardResponseDto extends Auditable {
    private String memberName;
    private String title;
    private String content;
    private long viewCount;

}
