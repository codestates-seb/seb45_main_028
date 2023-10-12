package com.mainproject.be28.domain.shopping.complain.dto;

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
public class ComplainPostDto {
        @Positive
        private Long ItemId;

        @NotBlank(message = "문의제목을 적어주세요")
        private String title;

        @NotBlank(message = "문의내용을 적어주세요")
        private String content;


}
