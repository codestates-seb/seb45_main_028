package com.mainproject.be28.cart.controller;

import com.mainproject.be28.cart.entity.Cart;
import com.mainproject.be28.cart.mapper.CartMapper;
import com.mainproject.be28.cart.service.CartService;
import com.mainproject.be28.cartItem.dto.CartItemDto;
import com.mainproject.be28.order.dto.CartOrderDto;
import com.mainproject.be28.order.entity.Order;
import com.mainproject.be28.response.SingleResponseDto;
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
    private final CartMapper mapper;
    private  final HttpStatus ok = HttpStatus.OK;

    @PostMapping
    public ResponseEntity addCart(@RequestBody @Valid CartItemDto cartItemDto){

        Cart cart = cartService.addCart(cartItemDto);

        HttpStatus ok = HttpStatus.OK;
        SingleResponseDto response = new SingleResponseDto<>(mapper.cartToCartResponseDto(cart),ok);
        return new ResponseEntity(response, ok);
    }

    @PostMapping("/reduce")
    public ResponseEntity reduceCart(@RequestBody @Valid CartItemDto cartItemDto){
// 전달받은 인증된 회원의 인증 토큰만 담기면,  생성된 Cart 객체에 Id와  담긴 회원 정보의 memberId만 추가해 넘겨주면 된다.
        cartItemDto.setCount(cartItemDto.getCount()*-1);
        Cart cart = cartService.addCart(cartItemDto);

        SingleResponseDto response = new SingleResponseDto<>(mapper.cartToCartResponseDto(cart),ok);
        return new ResponseEntity(response, ok);
    }
    @GetMapping
    public ResponseEntity getCart(){
        Cart cart = cartService.findCartByMember();
        SingleResponseDto response = new SingleResponseDto<>(mapper.cartToCartResponseDto(cart),ok);
        return new ResponseEntity(response, ok);
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity deleteCartItem(@PathVariable("itemId") long itemId) {
        cartService.removeItem(itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity deleteAll() {
        cartService.removeAllItem();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    // 장바구니 상품(들) 주문
    @PostMapping(value = "/orders/{memberId}")
    @ResponseBody
    public ResponseEntity orders(@RequestBody CartOrderDto cartOrderDto, @PathVariable("memberId") @Positive Long memberId ) {

    List<CartOrderDto> orderDtoList = cartOrderDto.getCartOrderItemList();

        if (orderDtoList == null || orderDtoList.size() == 0) {
            return new ResponseEntity<String>("주문할 상품을 선택해주세요.", HttpStatus.BAD_REQUEST);
        }
        Order order = new Order();
        Long orderId = cartService.orderCartItem(order,orderDtoList, memberId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

}
