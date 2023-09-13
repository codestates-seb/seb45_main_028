package com.mainproject.be28.order.dto;

import com.mainproject.be28.cartItem.entity.CartItem;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.orderItem.dto.OrderItemPostDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class OrderRequestDto {
    private long memberId;
    private long totalPrice;
    private String name;
    private String phone;
    private String address;


}

