package com.mainproject.be28.cart.service;

import com.mainproject.be28.cart.entity.Cart;
import com.mainproject.be28.cart.mapper.CartMapper;
import com.mainproject.be28.cart.repository.CartRepository;
import com.mainproject.be28.cartItem.dto.CartItemDto;
import com.mainproject.be28.cartItem.dto.CartItemResponseDto;
import com.mainproject.be28.cartItem.entity.CartItem;
import com.mainproject.be28.cartItem.repository.CartItemRepository;
import com.mainproject.be28.cartItem.service.CartItemService;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.item.service.ItemService;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.service.MemberService;
import com.mainproject.be28.order.data.OrderStatus;
import com.mainproject.be28.order.dto.CartOrderDto;
import com.mainproject.be28.order.entity.Order;
import com.mainproject.be28.order.repository.OrderRepository;
import com.mainproject.be28.order.service.OrderService;

import com.mainproject.be28.orderItem.entity.OrderItem;
import com.mainproject.be28.orderItem.repository.OrderItemRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service @Slf4j
public class CartService {
    private final MemberService memberService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemService cartItemService;
    private final ItemService itemService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartMapper cartMapperr;

    public CartService(MemberService memberService, CartRepository cartRepository, CartItemRepository cartItemRepository, CartItemService cartItemService, ItemService itemService, OrderService orderService, OrderRepository orderRepository, OrderItemRepository orderItemRepository, CartMapper cartMapperr) {
        this.memberService = memberService;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartItemService = cartItemService;
        this.itemService = itemService;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartMapperr = cartMapperr;
    }

    @Transactional
    public Cart addCart(CartItemDto cartItemDto) {
    Cart cart = findCartByMember();

    Item item =itemService.findItem(cartItemDto.getItemId());
    CartItem cartItem = cartItemService.findCartItem(cart, item);

    cartItem.addCount(cartItemDto.getCount());
    cartItem.setModifiedAt(LocalDateTime.now());

    addItemInCart(cart, cartItem);
    cartRepository.save(cart);

    return cart;
}
    public Cart findCartByMember() {
        Member member = memberService.findTokenMember();
        return cartRepository.findCartByMember(member).orElseGet(() -> cartRepository.save(Cart.createCart(member)));
    }

    public void removeItem(long itemId) { // 장바구니 내 개별 상품 제거
        Member member = memberService.findTokenMember();
        Cart cart = cartRepository.findCartByMember(member).orElseThrow(() -> new BusinessLogicException(ExceptionCode.CART_NOT_FOUND));
        CartItem cartItem = cartItemRepository.findCartItemByCart_CartIdAndItem_ItemId(cart.getCartId(), itemId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.CART_ITEM_NOT_FOUND));

        cartItemRepository.delete(cartItem);
    }
    public void removeAllItem() { // 장바구니 전체 삭제
        Cart cart = findCartByMember();
        cartRepository.delete(cart);
    }
    public Cart findCartByMemberId(Long memberId) {
        // memberId를 사용하여 회원 정보를 찾음
        Member member = memberService.findMember(memberId);
        return cartRepository.findCartByMember(member).orElse(null);
    }

    @Transactional
    public Order createCartOrder(CartOrderDto cartOrderDto) {
        Member member = memberService.findTokenMember();
        Cart cart = cartRepository.findCartByMember(member).orElseThrow(()
                -> new BusinessLogicException(ExceptionCode.CART_ITEM_NOT_FOUND));
        Order order =  new Order();
        order.setStatus(OrderStatus.NOT_PAID); // 주문 상태
        order.setMember(member);    //멤버정보
        order.makeOrderNumber(); // 주문 번호 만들기

        // 주문 항목 생성 및 저장
        List<OrderItem> orderItemList = new ArrayList<>();
        //카트항목 가져오기
        List<CartItemResponseDto> cartItems = cartMapperr.getCartItemsResponseDto(cart);
        //카트항목을 주문항목으로 바꿈
        for (CartItemResponseDto cartItem : cartItems) {
            // 아이템 정보 가져오기
            Item item = itemService.findItem(cartItem.getItemId());

            // 주문 항목 생성
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(cartItem.getCount());
            orderItem.setOrder(order);
            orderItem.setItem(item);

            // 주문 항목 목록에 추가
            orderItemList.add(orderItem);
        }
        order.setTotalPrice(getTotalPrice(orderItemList)); //총합가격
        order.setOrderItems(orderItemList); // 주문항목
        // 주문 저장
        orderRepository.save(order);
        //주문항목 저장
        orderItemRepository.saveAll(orderItemList);
        //주문 후 아이템 재고 삭제
        itemService.decreaseItemStock(cartOrderDto.getCartItemId(), cartOrderDto.getCount());
        //장바구니 삭제?? -> 장바구니 안에 있는 장바구니상품만 삭제
        removeAllItem();
        return order;
    }

    public long getTotalPrice(List<OrderItem> orderItems) {
        long price = 0;
        for (OrderItem orderItem : orderItems) {
            price += orderItem.getPrice() * orderItem.getQuantity();
        }
        return price;
    }

    private void addItemInCart(Cart cart, CartItem cartItem) {
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItemRepository.save(cartItem));
        cart.setCartItems(cartItemList);
    }


}
