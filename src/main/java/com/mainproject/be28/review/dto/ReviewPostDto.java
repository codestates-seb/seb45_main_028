package com.mainproject.be28.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPostDto {
    private Long itemId;
    private Long memberId;
    private String content;
    private int score;
}
