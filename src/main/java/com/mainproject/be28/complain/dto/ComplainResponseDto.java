package com.mainproject.be28.complain.dto;

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
        @Positive
        private Long complainId;
        @Positive
        private Long memberId;
        @Positive
        private Long ItemId;

        private String title;

        private String content;

        private String name;
        private String itemname;//추가
        private LocalDateTime modifiedAt;
        private LocalDateTime createdAt;

}
