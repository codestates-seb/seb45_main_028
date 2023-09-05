package com.mainproject.be28.cart.controller;

import com.mainproject.be28.cart.dto.CartDto;
import com.mainproject.be28.cart.entity.Cart;
import com.mainproject.be28.cart.mapper.CartMapper;
import com.mainproject.be28.cart.service.CartService;
import com.mainproject.be28.cartItem.dto.CartItemDto;
import com.mainproject.be28.cartItem.entity.CartItem;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Validated
public class CartController {

    private final CartService cartService;
    private final MemberService memberService;  // 회원 생성 시 권한 부여 목적.
    //    memberId를 param으로 받는 메서드는 모두 수정 필요!!
    private final CartMapper mapper;


    @PostMapping("{memberId}")
    public ResponseEntity addCart(@RequestBody @Valid CartItemDto cartItemDto,@PathVariable("memberId")long memberId){
// 전달받은 인증된 회원의 인증 토큰만 담기면,  생성된 Cart 객체에 Id와  담긴 회원 정보의 memberId만 추가해 넘겨주면 된다.

        cartService.addCart(cartItemDto,memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/reduce/{memberId}")
    public ResponseEntity reduceCart(@RequestBody @Valid CartItemDto cartItemDto,@PathVariable("memberId")long memberId){
// 전달받은 인증된 회원의 인증 토큰만 담기면,  생성된 Cart 객체에 Id와  담긴 회원 정보의 memberId만 추가해 넘겨주면 된다.
        cartItemDto.setCount(cartItemDto.getCount()*-1);
        cartService.addCart(cartItemDto,memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("{memberId}")
    public ResponseEntity getCart(@PathVariable("memberId")long memberId){
        Member member = memberService.findMember(memberId);
        Cart cart = cartService.findCartByMemberId(memberId);
        CartDto.Response cartOrResponse = mapper.cartToCartResponseDto(cart, member);
        return new ResponseEntity<>(cartOrResponse, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity deleteCartItem(@PathVariable("cartItemId") long cartItemId) {
        cartService.deleteCart(cartItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity deleteAll(@PathVariable("memberId") @Positive long memberId) {
        cartService.deleteCarts(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
