package com.mainproject.be28.domain.community.comment.dto;

import com.mainproject.be28.global.auditable.Auditable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentResponseDto extends Auditable {
    private String content;
    private String memberName;
    private Long likeCount;

}
