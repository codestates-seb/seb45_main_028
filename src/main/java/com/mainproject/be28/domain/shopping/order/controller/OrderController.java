package com.mainproject.be28.domain.shopping.order.controller;

import java.util.List;
import javax.validation.Valid;

import com.mainproject.be28.domain.shopping.order.dto.CartOrderDto;
import com.mainproject.be28.domain.shopping.order.dto.OrderPostDto;
import com.mainproject.be28.domain.shopping.order.dto.OrderResponseDto;
import com.mainproject.be28.domain.shopping.order.entity.Order;
import com.mainproject.be28.domain.shopping.order.mapper.OrderMapper;
import com.mainproject.be28.domain.shopping.service.ShoppingService;
import com.mainproject.be28.global.response.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/orders")
@Slf4j
public class OrderController {
    private final ShoppingService shoppingService;
    private final OrderMapper mapper;
    private  final HttpStatus ok = HttpStatus.OK;

    @PostMapping("/new")//상품에서 바로주문
    public ResponseEntity postOrder(@Valid @RequestBody OrderPostDto orderPostDto) {
        Order order = shoppingService.createOrder(orderPostDto);
        SingleResponseDto response =  new SingleResponseDto<>(mapper.ordersToOrderPageResponseDto(order),ok);
        return new ResponseEntity(response, ok);
    }

    @GetMapping("/checkout")// 결제정보
    public ResponseEntity getOrderToPay() { // 결제 창
        Order order = shoppingService.findOrder();
        SingleResponseDto response =  new SingleResponseDto<>(mapper.ordersToOrderPageResponseDto(order),ok);
        return new ResponseEntity(response, ok);
    }

    @GetMapping()//현재 사용자의 주문 내역을 조회
    public ResponseEntity getOrderMember() {
        List<Order> order = shoppingService.getOrdersByDateToList();
        List<OrderResponseDto> responses = mapper.OrdersToOrderResponseDtos(order);
        SingleResponseDto response =  new SingleResponseDto<>(responses,ok);
        return new ResponseEntity(response, ok);
    }
    @PostMapping( "/orders")
    public ResponseEntity postCartOrder(@RequestBody @Valid CartOrderDto cartOrderDto){
        Order createOrder = shoppingService.createCartOrder(cartOrderDto);
        SingleResponseDto response = new SingleResponseDto<>(mapper.ordersToOrderPageResponseDto(createOrder),ok);
        return new ResponseEntity<>(response,ok);
    }
    @DeleteMapping("/{order-id}")
    public ResponseEntity deleteOrder(@PathVariable("order-id") long orderId) {
        shoppingService.cancelOrder(orderId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}

