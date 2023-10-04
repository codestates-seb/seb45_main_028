package com.mainproject.be28.domain.shopping.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPatchDto {
  private Long itemId;
     private Long reviewId;
    private String content;
    private Long score;
}
