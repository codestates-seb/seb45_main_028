package com.mainproject.be28.domain.shopping.order.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderRequestDto {
    private long memberId;
    private long totalPrice;
    private String name;
    private String phone;
    private String address;


}

