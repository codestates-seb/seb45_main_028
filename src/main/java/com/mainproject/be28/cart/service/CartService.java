package com.mainproject.be28.cart.service;

import com.mainproject.be28.cart.entity.Cart;
import com.mainproject.be28.cart.repository.CartRepository;
import com.mainproject.be28.cartItem.dto.CartItemDto;
import com.mainproject.be28.cartItem.entity.CartItem;
import com.mainproject.be28.cartItem.repository.CartItemRepository;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.item.repository.ItemRepository;
import com.mainproject.be28.item.service.ItemService;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.repository.MemberRepository;
import com.mainproject.be28.member.service.MemberService;
import com.mainproject.be28.order.dto.CartOrderDto;
import com.mainproject.be28.order.dto.OrderPostDto;
import com.mainproject.be28.order.entity.Order;
import com.mainproject.be28.order.service.OrderService;
import com.mainproject.be28.orderItem.dto.OrderItemPostDto;
import com.mainproject.be28.utils.CustomBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final CustomBeanUtils<Cart> beanUtils;
    private final OrderService orderService;

    public CartService(MemberService memberService
            , MemberRepository memberRepository, CartRepository cartRepository, ItemRepository itemRepository, CartItemRepository cartItemRepository
            , ItemService itemService, CustomBeanUtils<Cart> beanUtils, OrderService orderService) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.itemService = itemService;
        this.beanUtils = beanUtils;
        this.orderService = orderService;
    }
@Transactional
    public CartItem addCart(CartItemDto cartItemDto, long memberId) {
//       멤버 토큰 전달 후 구현
//       Member member = memberRepository.findById(memberId);
//        Cart cart = cartRepository.findByMemberId(member.getMemberId());

    Optional<Member> optionalMember = memberRepository.findById(memberId);
    Member member = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    Cart cart = cartRepository.findCartByMember_MemberId(memberId);

    if (cart == null) {
        cart = Cart.createCart(member);
        cartRepository.save(cart);
    }

    Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(() -> new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND));
    CartItem cartItem = cartItemRepository.findCartItemByCart_CartIdAndItem_ItemId(cart.getCartId(), item.getItemId());

    // 해당 상품이 장바구니에 존재하지 않는다면 생성 후 추가
    if (cartItem == null) {
        cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());

        // 해당 상품이 장바구니에 이미 존재한다면 수량을 증가
    } else {
        cartItem.addCount(cartItemDto.getCount());
        cartItem.setModifiedAt(LocalDateTime.now());
    }
    return cartItemRepository.save(cartItem);
}

    public Cart findCartByMemberId(Long memberId) {
        Cart cart = cartRepository.findCartByMember_MemberId(memberId);
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member member = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        if(cart == null){ Cart.createCart(member);}
        return cart;
    }
    public void deleteCart(long cartItemId) { // 장바구니 내 개별 상품 제거
        CartItem cartItem = cartItemRepository.findByCartItemId(cartItemId);
        cartItemRepository.delete(cartItem);
    }
    public void deleteCarts(Long memberId) { // 장바구니 전체 삭제
        Cart cart = findCartByMemberId(memberId);
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
