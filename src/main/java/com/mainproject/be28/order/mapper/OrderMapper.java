package com.mainproject.be28.order.mapper;

import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.order.dto.*;
import com.mainproject.be28.order.entity.Order;

import com.mainproject.be28.orderItem.dto.OrderItemPostDto;
import com.mainproject.be28.orderItem.dto.OrderItemResponseDto;
import com.mainproject.be28.orderItem.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "memberId",target = "member.memberId")
    Order cartOrderPostDtoToOrders(CartOrderDto cartOrdertDto);
    @Mapping(source = "memberId",target = "member.memberId")
    Order orderPostDtoToOrders(OrderPostDto orderPostDto);
    default OrderPageResponseDto ordersToOrderPageResponseDto(Order order) {
        OrderPageResponseDto orderPageResponseDto = new OrderPageResponseDto();
        orderPageResponseDto.setOrderNumber(order.getOrderNumber());
        orderPageResponseDto.setAddress(order.getMember().getAddress());
        orderPageResponseDto.setName(order.getMember().getName());
        orderPageResponseDto.setPhone(order.getMember().getPhone());
        orderPageResponseDto.setTotalPrice(order.getTotalPrice());
        orderPageResponseDto.setEmail(order.getMember().getEmail());
        return orderPageResponseDto;
    }
    default List<OrderResponseDto> OrdersToOrderResponseDtos(List<Order> orders) {
        if (orders == null) return null;
        // 주문 목록을 스트림으로 변환하고, 각 주문을 OrderResponseDto로 변환하는 람다 표현식을 사용하여 매핑
        List<OrderResponseDto> orderResponseDtos = orders.stream().map(order -> {
            OrderResponseDto orderResponseDto = new OrderResponseDto();
            orderResponseDto.setName(order.getMember().getName());
            orderResponseDto.setOrderNumber(order.getOrderNumber());
            orderResponseDto.setCreatedAt(order.getCreatedAt());
            orderResponseDto.setOrderStatus(order.getStatus());
            orderResponseDto.setTotalPrice(order.getTotalPrice());

            // 주문 상품 목록을 스트림으로 변환하고, 각 주문 상품을 OrderItemResponseDto로 변환
            List<OrderItem> orderItems = order.getOrderItems();
            List<OrderItemResponseDto> orderItemResponseList = orderItems.stream().map(orderItem -> {
                OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto();
                orderItemResponseDto.setName(orderItem.getItem().getName());
                orderItemResponseDto.setPrice(orderItem.getItem().getPrice());
                orderItemResponseDto.setQuantity(orderItem.getQuantity());
                return orderItemResponseDto;
            }).collect(Collectors.toList());

            orderResponseDto.setItems(orderItemResponseList);
            return orderResponseDto;
        }).collect(Collectors.toList());

        return orderResponseDtos;
    }
}






