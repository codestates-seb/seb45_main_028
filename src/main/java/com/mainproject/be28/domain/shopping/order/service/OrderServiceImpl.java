package com.mainproject.be28.domain.shopping.order.service;

import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.member.service.Layer2.MemberVerifyService;
import com.mainproject.be28.domain.shopping.cart.entity.Cart;
import com.mainproject.be28.domain.shopping.cart.entity.CartItem;
import com.mainproject.be28.domain.shopping.cart.service.CartService;
import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.domain.shopping.item.service.ItemService;
import com.mainproject.be28.domain.shopping.order.data.OrderStatus;
import com.mainproject.be28.domain.shopping.order.dto.CartOrderDto;
import com.mainproject.be28.domain.shopping.order.dto.OrderPostDto;
import com.mainproject.be28.domain.shopping.order.entity.Order;
import com.mainproject.be28.domain.shopping.order.entity.OrderItem;
import com.mainproject.be28.domain.shopping.order.repository.OrderItemRepository;
import com.mainproject.be28.domain.shopping.order.repository.OrderRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final MemberVerifyService memberVerifyService;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;


    public OrderServiceImpl (OrderRepository orderRepository, ItemService itemService, MemberVerifyService memberVerifyService,
                        OrderItemRepository orderItemRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
        this.memberVerifyService = memberVerifyService;
        this.orderItemRepository = orderItemRepository;
        this.cartService = cartService;
    }
    @Override
    public Order createOrder(OrderPostDto orderPostDto)   {
        Order order = makeOrder();
        order.setOrderNumber(makeOrderNumber());

        //todo: 결제로 넘김.
        //주문이 완료되면 재고 줄어듦
        itemService.decreaseItemStock(orderPostDto.getItemId(), orderPostDto.getQuantity());

        setOrderItems(orderPostDto, order);

        return order;
    }


    private OrderItem orderItemPostDtoToOrderItem(OrderPostDto orderPostDto, Order order) {
        Item item = itemService.verifyExistItem(orderPostDto.getItemId());

        return OrderItem.builder()
                .price(item.getPrice())
                .quantity(orderPostDto.getQuantity())
                .order(order)
                .item(item)
                .build();
    }
    @Override
    public Order findOrder(){
        Member member = memberVerifyService.findTokenMember();
        Optional<Order> order = orderRepository.findOrderByMember(member);
        return order.orElseGet(this::makeOrder);
    }

    //주문내역 확인
    public List<Order> getOrdersByDateToList() {
        Member member = memberVerifyService.findTokenMember();
        return orderRepository.findByMemberOrderByCreatedAtDesc(member);// creat_At을 기준으로 내림차순으로
    }


    @Override
    public void cancelOrder(long itemId)  {
        Order order = findOrder();
        order.setStatus(OrderStatus.ORDER_CANCELED);
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder()  {
        Order order = findOrder();
        orderRepository.delete(order);
    }

    @Override
    @Transactional
    public Order createCartOrder(CartOrderDto cartOrderDto){
        Cart cart = cartService.findCartByMember();

        Order order = makeOrder();

        order.setOrderNumber(makeOrderNumber()); // 주문 번호 만들기

        // 주문 항목 생성 및 저장
        List<OrderItem> orderItemList = cartToOrder(cart, order);
        // 주문 저장
        orderRepository.save(order);
        //주문항목 저장
        orderItemRepository.saveAll(orderItemList);
        //주문 후 아이템 재고 삭제
        itemService.decreaseItemStock(cartOrderDto.getCartItemId(), cartOrderDto.getCount());
        //장바구니 삭제?? -> 장바구니 안에 있는 장바구니상품만 삭제
        cartService.removeAllItem();
        return order;
    }

    @NotNull
    private List<OrderItem> cartToOrder(Cart cart, Order order) {
        List<OrderItem> orderItemList = new ArrayList<>();
        //카트항목 가져오기
        List<CartItem> cartItems = cart.getCartItems();
        //카트항목을 주문항목으로 바꿈
        for (CartItem cartItem : cartItems) {
            // 아이템 정보 가져오기
            Item item = cartItem.getItem();

            // 주문 항목 생성
            OrderItem orderItem = OrderItem.builder()
                    .price(item.getPrice())
                    .quantity(cartItem.getCount())
                    .order(order)
                    .item(item)
                    .build();

            // 주문 항목 목록에 추가
            orderItemList.add(orderItem);
        }
        order.setTotalPrice(cartService.getTotalPrice(orderItemList)); //총합가격
        order.setOrderItems(orderItemList); // 주문항목
        return orderItemList;
    }

    private void setOrderItems(OrderPostDto orderPostDto, Order order) {
        OrderItem orderItem= orderItemPostDtoToOrderItem(orderPostDto, order);
        List<OrderItem> items = new ArrayList<>();
        items.add(orderItem);
        order.setOrderItems(items);
        order.setTotalPrice(orderItem.getQuantity() * orderItem.getPrice());
    }

    @NotNull
    private Order makeOrder() {
        Member member = memberVerifyService.findTokenMember();
        Order order = Order.builder()
                .member(member)
                .status(OrderStatus.NOT_PAID)
                .build();

        orderRepository.save(order);
        return order;
    }

    private String makeOrderNumber() {
        String date = ZonedDateTime.now(ZoneId.of("UTC")).plusHours(9).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return date + RandomStringUtils.randomNumeric(6);
    }
}
