package com.mainproject.be28.domain.shopping.complain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplainResponseDto {


        private String title;

        private String content;

        private String name;
        private String itemName;//추가
        private LocalDateTime modifiedAt;
        private LocalDateTime createdAt;

}
