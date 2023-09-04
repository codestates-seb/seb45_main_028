package com.mainproject.be28.cart.dto;

import com.mainproject.be28.cartItem.dto.CartItemDto;
import com.mainproject.be28.cartItem.entity.CartItem;
import com.mainproject.be28.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;
public class CartDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        private long memberId;
        private List<CartItemDto> cartItems;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Patch {
        private long memberId;
        private List<CartItemDto> cartItems;
    }
    public static class Response {
        private long cartId;
        private long memberId;
        private List<CartItemDto> cartItems;
        private LocalDateTime createdAt;
    }
}
