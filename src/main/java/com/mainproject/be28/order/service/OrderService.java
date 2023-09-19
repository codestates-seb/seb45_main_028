package com.mainproject.be28.order.service;


import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.item.repository.ItemRepository;
import com.mainproject.be28.item.service.ItemService;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.repository.MemberRepository;
import com.mainproject.be28.member.service.MemberService;
import com.mainproject.be28.order.data.OrderStatus;
import com.mainproject.be28.order.dto.OrderPostDto;
import com.mainproject.be28.order.entity.Order;
import com.mainproject.be28.order.repository.OrderRepository;
import com.mainproject.be28.orderItem.entity.OrderItem;
import com.mainproject.be28.orderItem.repository.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    //주문생성
    public Order createOrder(OrderPostDto orderPostDto)   {
        Member member = memberService.findTokenMember();
        Order order = new Order();
        order.makeOrderNumber();
        order.setMember(member);
        order.setStatus(OrderStatus.NOT_PAID);

        orderRepository.save(order);
        OrderItem orderItem= orderItemPostDtoToOrderItem(orderPostDto, order);
//        List<OrderItem> orderItems = new ArrayList<>();
//        orderItems.add(orderItem);
//        order.setOrderItems(orderItems);
        orderItemRepository.save(orderItem);
        order.setTotalPrice(orderItem.getQuantity()*orderItem.getPrice());
        return order;
    }

    public OrderItem orderItemPostDtoToOrderItem(OrderPostDto orderPostDto,Order order) {
//        List<OrderItem> orderItems = new ArrayList<>(); //orderItems 빈 리스트 생성

//        for (OrderItemPostDto orderItemPostDto : orderItemPostDtos) { // orderItems 리스트에 추가
            Item item = itemService.findItem(orderPostDto.getItemId());
            long quantity = orderPostDto.getQuantity();

            OrderItem orderItem = new OrderItem(quantity, item); // 중복 생성을 방지하기 위해 생성자를 이용
//            orderItem.addOrder(order);
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(quantity);
            orderItem.setOrder(order);
        return orderItem;
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
    public Order findOrder() {
        Member member = memberService.findTokenMember();
        Optional<Order> order = orderRepository.findOrderByMember(member);
        return order.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
    }

    //주문내역 확인
    public List<Order> getOrdersByDateToList() {
        Member member = memberService.findTokenMember();
        return orderRepository.findByMemberOrderByCreatedAtDesc(member);// creat_At을 기준으로 내림차순으로
    }
    //멤버토큰으로 주문찾기
    public Order findOrderByMember() {
        Member member = memberService.findTokenMember();
        return orderRepository.findOrderByMember(member).orElseGet(()
                -> orderRepository.save(Order.createOrder(member)));
    }

    //주문취소(상태만 바꿈)
    public void cancelOrder(long itemId) {
        Order order = findOrder();
        OrderItem orderitem = orderItemRepository.findByItem_ItemId(itemId).orElseThrow(()->new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND));
        if (order != null) {
            order.setStatus(OrderStatus.ORDER_CANCELED);
            orderRepository.save(order);
        }
    }
    //데이터배이스에서 삭제
    public void deleteOrder() {
        Order order = findOrder();
        if (order != null) {
            orderRepository.delete(order);
        }
    }







}
