package com.mainproject.be28.order.controller;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

import com.mainproject.be28.order.dto.OrderPageResponseDto;
import com.mainproject.be28.order.dto.OrderPostDto;
import com.mainproject.be28.order.dto.OrderResponseDto;
import com.mainproject.be28.order.entity.Order;
import com.mainproject.be28.order.mapper.OrderMapper;
import com.mainproject.be28.order.service.OrderService;
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
    private final OrderService orderService;
    private final OrderMapper mapper;

    @PostMapping("/{memberId}")//주문생성(아이템아이디,개수)
    public ResponseEntity<?> postOrder(@Valid @RequestBody OrderPostDto orderPostDto,
                                       @PathVariable("memberId") long memberId) throws IOException {

        Order order = new Order();
        Order createdOrder = orderService.createOrder(order, orderPostDto, memberId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/checkout/{order-id}")//특정 주문을 찾고, 해당 주문이 현재 사용자인지 확인
    public ResponseEntity<?> getOrderToPay(@PathVariable("order-id") @PositiveOrZero long orderId,
                                           @RequestParam("memberId") long memberId) { // 결제 창
        Order order = orderService.findOrder(orderId);
        orderService.checkOrderHolder(order, memberId);
        OrderPageResponseDto response = mapper.ordersToOrderPageResponseDto(order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{memberId}")//현재 사용자의 주문 내역을 조회
    public ResponseEntity<?> getOrderMember(@PathVariable("memberId") long memberId) {
        List<Order> order = orderService.getOrdersByDateToList(memberId);
        List<OrderResponseDto> response = mapper.OrdersToOrderResponseDtos(order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{order-number}")
    public ResponseEntity<?> deleteOrder(@PathVariable("order-number") String orderNumber,
                                         @RequestParam("memberId") long memberId) {
        orderService.checkOrderHolder(orderNumber,memberId);
        orderService.cancelOrder(orderNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

