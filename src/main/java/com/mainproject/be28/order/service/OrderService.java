package com.mainproject.be28.order.service;


import com.mainproject.be28.cart.entity.Cart;
import com.mainproject.be28.cartItem.repository.CartItemRepository;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.item.repository.ItemRepository;
import com.mainproject.be28.item.service.ItemService;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.repository.MemberRepository;
import com.mainproject.be28.member.service.MemberService;
import com.mainproject.be28.order.data.OrderStatus;
import com.mainproject.be28.order.dto.OrderPatchStatusDto;
import com.mainproject.be28.order.dto.OrderPostDto;
import com.mainproject.be28.order.entity.Order;
import com.mainproject.be28.order.repository.OrderRepository;
import com.mainproject.be28.orderItem.dto.OrderItemPostDto;
import com.mainproject.be28.orderItem.entity.OrderItem;
import com.mainproject.be28.orderItem.repository.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;
    @Transactional
    //주문생성
    public Order createOrder(Order order, OrderPostDto orderPostDto)   {
        Member member = memberService.findMember(orderPostDto.getMemberId());



        order.makeOrderNumber();
        order.setMember(member);
        order.setStatus(OrderStatus.NOT_PAID);

        orderRepository.save(order);

        OrderItemPostDtoToOrdersItem(orderPostDto.getOrderItems(), order);


        return order;
    }

    @Transactional
    //// ItemId 와 quantity, member로 OrdersItem를 저장
    public void OrderItemPostDtoToOrdersItem(List<OrderItemPostDto> orderItemPostDtos, Order order) {
        List<OrderItem> orderItems = new ArrayList<>(); //orderItems 빈 리스트 생성

        for (OrderItemPostDto orderItemPostDto : orderItemPostDtos) { // orderItems 리스트에 추가
            Item item = itemService.findItem(orderItemPostDto.getItemId());
            long quantity = orderItemPostDto.getQuantity();

            OrderItem orderItem = new OrderItem(quantity, item); // 중복 생성을 방지하기 위해 생성자를 이용
            orderItem.addOrder(order);
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(quantity);

            orderItems.add(orderItem);
        }

        long totalPrice = getTotalPrice(orderItems);
        order.setTotalPrice(totalPrice);

    }

    //총합 가격
    public long getTotalPrice(List<OrderItem> orderItems) {
        long price = 0;
        for(OrderItem orderItem : orderItems){
            price += orderItem.getPrice() * orderItem.getQuantity();
        }
        return price;
    }


    //주문아이디로 주문찾기
    public Order findOrder(long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
    }

    //주문내역 확인
    public List<Order> getOrdersByDateToList(long memberId) {
        Member member = memberService.findVerifiedMember(memberId);
        return orderRepository.findByMemberOrderByCreatedAtDesc(member);// creat_At을 기준으로 내림차순으로
    }
    //멤버토큰으로 주문찾기
    public Order findOrderByMember() {
        Member member = memberService.findTokenMember();
        return orderRepository.findOrderByMember(member).orElseGet(()
                -> orderRepository.save(Order.createOrder(member)));
    }

    //주문취소(상태만 바꿈)
    public void cancelOrder(long orderId) {
        Order order = findOrder(orderId);
        if (order != null) {
            order.setStatus(OrderStatus.ORDER_CANCELED);
            orderRepository.save(order);
        }
    }
    //데이터배이스에서 삭제
    public void deleteOrder(long orderId) {
        Order order = findOrder(orderId);
        if (order != null) {
            orderRepository.delete(order);
        }
    }







}
