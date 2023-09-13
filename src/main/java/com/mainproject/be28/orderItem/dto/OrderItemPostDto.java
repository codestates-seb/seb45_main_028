package com.mainproject.be28.orderItem.dto;

import lombok.Getter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

    @Getter
    public class OrderItemPostDto {  // OrderPostDto에 들어감

        @PositiveOrZero
        private long itemId;
        @Positive
        private int quantity;
    }
