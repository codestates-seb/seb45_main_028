package com.mainproject.be28.comment.dto;

import com.mainproject.be28.auditable.Auditable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentResponseDto extends Auditable {
    private String content;
    private String memberName;
    private Long likeCount;

}
