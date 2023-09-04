package com.mainproject.be28.complain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplainResponsesDto {
    @Positive
    private Long complainId;
    @Positive
    private Long ItemId;
    @Positive
    private Long memberId;

    private String name;
    private String itemname;

    private String title;


}
