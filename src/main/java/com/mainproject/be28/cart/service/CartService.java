package com.mainproject.be28.cart.service;

import com.mainproject.be28.cart.entity.Cart;
import com.mainproject.be28.cart.repository.CartRepository;
import com.mainproject.be28.cartItem.dto.CartItemDto;
import com.mainproject.be28.cartItem.entity.CartItem;
import com.mainproject.be28.cartItem.repository.CartItemRepository;
import com.mainproject.be28.cartItem.service.CartItemService;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.item.service.ItemService;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.service.MemberService;
import com.mainproject.be28.order.dto.CartOrderDto;
import com.mainproject.be28.order.entity.Order;
import com.mainproject.be28.order.service.OrderService;
import com.mainproject.be28.orderItem.dto.OrderItemPostDto;
import com.mainproject.be28.utils.CustomBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class CartService {
    private final MemberService memberService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemService cartItemService;
    private final ItemService itemService;
    private final CustomBeanUtils<Cart> beanUtils;
    private final OrderService orderService;

    public CartService(MemberService memberService, ItemService itemService, OrderService orderService, CartItemService cartItemService,
                       CartRepository cartRepository, CartItemRepository cartItemRepository,
                       CustomBeanUtils<Cart> beanUtils) {
        this.memberService = memberService;
        this.itemService = itemService;
        this.orderService = orderService;
        this.cartItemService = cartItemService;

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;

        this.beanUtils = beanUtils;

    }
@Transactional
    public Cart addCart(CartItemDto cartItemDto) {

    Cart cart = findCartByMember();

    Item item = itemService.findItem(cartItemDto.getItemId());
    CartItem cartItem = cartItemService.findCartItem(cart, item);
    cartItem.addCount(cartItemDto.getCount());
    cartItem.setModifiedAt(LocalDateTime.now());
    List<CartItem> cartItemList = new ArrayList<>();
    cartItemList.add(cartItemRepository.save(cartItem));
    cart.setCartItems(cartItemList);
    cartRepository.save(cart);

    return cart;
}

    public Cart findCartByMember() {
        Member member = memberService.findTokenMember();
        return cartRepository.findCartByMember(member).orElseGet(() -> cartRepository.save(Cart.createCart(member)));
    }

    public void deleteCart(long cartItemId) { // 장바구니 내 개별 상품 제거
        CartItem cartItem = cartItemRepository.findByCartItemId(cartItemId);
        cartItemRepository.delete(cartItem);
    }
    public void deleteCarts() { // 장바구니 전체 삭제
        Cart cart = findCartByMember();
        cartRepository.delete(cart);
    }

    // 장바구니 상품(들) 주문
    public Long orderCartItem(Order order, List<CartOrderDto> cartOrderDtoList, Long memberId) {
        List<OrderItemPostDto> orderDtoList = new ArrayList<>();

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId())
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.CART_NOT_FOUND));

            // CartOrderDto를 OrderItemPostDto로 변환
            OrderItemPostDto orderDto = new OrderItemPostDto();
            orderDto.setItemId(cartItem.getItem().getItemId());
            orderDto.setQuantity(cartItem.getCount());

            // OrderItemPostDto를 리스트에 추가
            orderDtoList.add(orderDto);
        }

        // 주문 생성 메서드 호출
        Long orderId = orderService.orders(order, orderDtoList, memberId);

        // 주문한 장바구니 상품을 제거
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId())
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.CART_NOT_FOUND));
            cartItemRepository.delete(cartItem);
        }

        return orderId;
    }


}
