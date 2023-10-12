package com.mainproject.be28.domain.shopping.order.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class CartOrderDto {
    private long cartItemId;
    @Min(value = 1, message = "최소 1개 이상 담아주세요")
    private Long count;
}