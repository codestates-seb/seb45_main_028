package com.mainproject.be28.domain.shopping.order.mapper;

import com.mainproject.be28.domain.shopping.order.dto.OrderPageResponseDto;
import com.mainproject.be28.domain.shopping.order.dto.OrderResponseDto;
import com.mainproject.be28.domain.shopping.order.entity.Order;
import com.mainproject.be28.domain.shopping.order.dto.OrderItemResponseDto;
import com.mainproject.be28.domain.shopping.order.entity.OrderItem;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface OrderMapper {

    default OrderPageResponseDto ordersToOrderPageResponseDto(Order order) {
        OrderPageResponseDto orderPageResponseDto = new OrderPageResponseDto();
        orderPageResponseDto.setOrderNumber(order.getOrderNumber());
        orderPageResponseDto.setAddress(order.getMember().getAddress());
        orderPageResponseDto.setMemberName(order.getMember().getName());
        orderPageResponseDto.setPhone(order.getMember().getPhone());
        orderPageResponseDto.setTotalPrice(order.getTotalPrice());
        orderPageResponseDto.setEmail(order.getMember().getEmail());

        List<OrderItem> orderItems = order.getOrderItems();

        // OrderItem 정보를 리스트로 저장하기 위한 리스트 초기화
        List<OrderItemResponseDto> orderItemResponseList = new ArrayList<>();

        // 주문 항목 정보를 매핑하여 리스트에 추가
        for (OrderItem orderItem : orderItems) {
            OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto();
            orderItemResponseDto.setItemName(orderItem.getItem().getName()); // 아이템의 이름 설정
            orderItemResponseDto.setPrice(orderItem.getPrice()); // 아이템의 가격 설정
            orderItemResponseDto.setQuantity(orderItem.getQuantity()); // 수량 설정
            orderItemResponseList.add(orderItemResponseDto);
        }

        // 주문 항목 리스트를 설정
        orderPageResponseDto.setOrderItems(orderItemResponseList);

        return orderPageResponseDto;
    }
    default List<OrderResponseDto> OrdersToOrderResponseDtos(List<Order> orders) {
        if (orders == null) return null;
        // 주문 목록을 스트림으로 변환하고, 각 주문을 OrderResponseDto로 변환하는 람다 표현식을 사용하여 매핑
        List<OrderResponseDto> orderResponseDtos = orders.stream().map(order -> {
            OrderResponseDto orderResponseDto = new OrderResponseDto();
            orderResponseDto.setMemberName(order.getMember().getName());
            orderResponseDto.setOrderNumber(order.getOrderNumber());
            orderResponseDto.setCreatedAt(order.getCreatedAt());
            orderResponseDto.setOrderStatus(order.getStatus());
            orderResponseDto.setTotalPrice(order.getTotalPrice());

            // 주문 상품 목록을 스트림으로 변환하고, 각 주문 상품을 OrderItemResponseDto로 변환
            List<OrderItem> orderItems = order.getOrderItems();
            List<OrderItemResponseDto> orderItemResponseList = orderItems.stream().map(orderItem -> {
                OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto();
                orderItemResponseDto.setItemName(orderItem.getItem().getName());
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






