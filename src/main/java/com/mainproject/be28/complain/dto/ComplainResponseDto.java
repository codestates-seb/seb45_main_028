package com.mainproject.be28.complain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

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

        @NotBlank(message = "문의내용을 적어주세요")
        private String content;


}
