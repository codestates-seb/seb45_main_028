package com.mainproject.be28.complain.dto;

import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.member.entity.Member;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ComplainPatchDto {
            private Long complainId;
            private String title;

            private String content;

        }


