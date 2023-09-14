package com.mainproject.be28.orderItem.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

    @Getter
    @Setter
    public class OrderItemPostDto {  // OrderPostDto에 들어감

        @PositiveOrZero
        private long itemId;

        @NotNull
        @Min(value = 1, message = "최소 주문 수량은 1개 입니다.")
        private Long quantity;
    }
