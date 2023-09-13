package com.mainproject.be28.review.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDto {
    private Long reviewId;
    private String itemName;
    private Long memberId;
    private String memberName;
    private String content;
    private  int score;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private Long likeCount;
    private Long unlikeCount;
}