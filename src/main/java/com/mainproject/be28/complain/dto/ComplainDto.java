package com.mainproject.be28.complain.dto;

import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.member.entity.Member;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ComplainDto {

        @Setter
        @Getter
        public static class Patch {

            private Long memberId;
            private Long complainId;
            private Long itemId;
            @NotBlank(message = "문의내용을 적어주세요")
            private String content;

        }

        @Setter
        @Getter
        public static class ListResponse{
            private Long memberId;
            private Long itemId;
            private String content;


        }

        public class DeleteRequest {
            private Long complainId;
        }
    }

