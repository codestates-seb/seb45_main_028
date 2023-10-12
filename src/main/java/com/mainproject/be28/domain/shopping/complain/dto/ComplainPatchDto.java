package com.mainproject.be28.domain.shopping.complain.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ComplainPatchDto {
            private Long complainId;
            private String title;

            private String content;

        }


