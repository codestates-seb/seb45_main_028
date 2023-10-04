package com.mainproject.be28.domain.shopping.review.dto;

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
    private String content;
    private int score;
}
