package com.mainproject.be28.domain.shopping.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
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
@Getter
@AllArgsConstructor
    public static class Response {
        private List<CartItemResponseDto> cartItems;
        private long totalPrice;

    }
}
