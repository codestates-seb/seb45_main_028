package com.mainproject.be28.domain.shopping.review.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDto {
    private String itemName;
    private String memberName;
    private String content;
    private  int score;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private Long likeCount;
    private Long unlikeCount;
}