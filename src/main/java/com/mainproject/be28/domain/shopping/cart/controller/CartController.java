package com.mainproject.be28.domain.shopping.cart.controller;

import com.mainproject.be28.domain.shopping.cart.dto.CartDto;
import com.mainproject.be28.domain.shopping.cart.entity.Cart;
import com.mainproject.be28.domain.shopping.cart.mapper.CartMapper;
import com.mainproject.be28.domain.shopping.cart.service.CartService;
import com.mainproject.be28.domain.shopping.cart.dto.CartItemDto;
import com.mainproject.be28.global.response.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Validated
public class CartController {

    private final CartService cartService;
    private final CartMapper mapper;
    private  final HttpStatus ok = HttpStatus.OK;

    @PostMapping
    public ResponseEntity<SingleResponseDto<CartDto.Response>> addCart(@RequestBody @Valid CartItemDto cartItemDto){

        Cart cart = cartService.addCart(cartItemDto);

        HttpStatus ok = HttpStatus.OK;
        SingleResponseDto<CartDto.Response> response = new SingleResponseDto<>(mapper.cartToCartResponseDto(cart),ok);
        return new ResponseEntity<>(response, ok);
    }

    @PostMapping("/reduce")
    public ResponseEntity<SingleResponseDto<CartDto.Response>> reduceCart(@RequestBody @Valid CartItemDto cartItemDto){
// 전달받은 인증된 회원의 인증 토큰만 담기면,  생성된 Cart 객체에 Id와  담긴 회원 정보의 memberId만 추가해 넘겨주면 된다.
        cartItemDto.setCount(cartItemDto.getCount()*-1);
        Cart cart = cartService.addCart(cartItemDto);

        SingleResponseDto<CartDto.Response> response = new SingleResponseDto<>(mapper.cartToCartResponseDto(cart),ok);
        return new ResponseEntity<>(response, ok);
    }
    @GetMapping
    public ResponseEntity<SingleResponseDto<CartDto.Response>> getCart(){
        Cart cart = cartService.findCartByMember();
        SingleResponseDto<CartDto.Response> response = new SingleResponseDto<>(mapper.cartToCartResponseDto(cart),ok);
        return new ResponseEntity<>(response, ok);
    }


    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<HttpStatus> deleteCartItem(@PathVariable("itemId") long itemId) {
        cartService.removeItem(itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll() {
        cartService.removeAllItem();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
