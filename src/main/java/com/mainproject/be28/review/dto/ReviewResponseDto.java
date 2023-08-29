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
    private Long itemId;
    private Long memberId;
    private String name;
    private String content;
    private LocalDateTime createdAt;
    private Long likeCount;
    private Long unlikeCount;
}