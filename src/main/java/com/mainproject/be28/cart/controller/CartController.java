package com.mainproject.be28.cart.controller;

import com.mainproject.be28.cart.dto.CartDto;
import com.mainproject.be28.cart.entity.Cart;
import com.mainproject.be28.cart.mapper.CartMapper;
import com.mainproject.be28.cart.service.CartService;
import com.mainproject.be28.cartItem.dto.CartItemDto;
import com.mainproject.be28.item.dto.ItemDto;
import com.mainproject.be28.item.mapper.ItemMapper;
import com.mainproject.be28.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Validated
public class CartController {

    private final CartService cartService;
    //    private final MemberService memberService;  // 회원 생성 시 권한 부여 목적.
    private final CartMapper mapper;


//    @PostMapping
//    public ResponseEntity addCart(@RequestBody @Valid CartItemDto cartItemDto){
//// 전달받은 인증된 회원의 인증 토큰만 담기면,  생성된 Cart 객체에 Id와  담긴 회원 정보의 memberId만 추가해 넘겨주면 된다.
//        Long memberId = null;
//
//        cartService.addCart(cartItemDto, memberId);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
