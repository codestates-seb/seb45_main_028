package com.mainproject.be28.complain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
@Getter
@Setter
@NoArgsConstructor
public class ComplainPostDto {
        @Positive
        private long memberId;

        @Positive
        private Long ItemId;

        @NotBlank(message = "문의내용을 적어주세요")
        private String content;

}
