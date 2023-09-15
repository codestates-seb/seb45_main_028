package com.mainproject.be28.cart.service;

import com.mainproject.be28.cart.entity.Cart;
import com.mainproject.be28.cart.repository.CartRepository;
import com.mainproject.be28.cartItem.dto.CartItemDto;
import com.mainproject.be28.cartItem.entity.CartItem;
import com.mainproject.be28.cartItem.repository.CartItemRepository;
import com.mainproject.be28.cartItem.service.CartItemService;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.item.service.ItemService;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.service.MemberService;
import com.mainproject.be28.order.data.OrderStatus;
import com.mainproject.be28.order.dto.CartOrderDto;
import com.mainproject.be28.order.entity.Order;
import com.mainproject.be28.order.repository.OrderRepository;
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
    private final OrderRepository orderRepository;

    public CartService(MemberService memberService, ItemService itemService, OrderService orderService, CartItemService cartItemService,
                       CartRepository cartRepository, CartItemRepository cartItemRepository,
                       CustomBeanUtils<Cart> beanUtils, OrderRepository orderRepository) {
        this.memberService = memberService;
        this.itemService = itemService;
        this.orderService = orderService;
        this.cartItemService = cartItemService;

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;

        this.beanUtils = beanUtils;

        this.orderRepository = orderRepository;
    }
@Transactional
    public Cart addCart(CartItemDto cartItemDto) {

    Cart cart = findCartByMember();

    Item item = itemService.findItem(cartItemDto.getItemId());
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

    public void removeItem(long cartItemId) { // 장바구니 내 개별 상품 제거
        CartItem cartItem = cartItemRepository.findByCartItemId(cartItemId);
        cartItemRepository.delete(cartItem);
    }
    public void removeAllItem() { // 장바구니 전체 삭제
        Cart cart = findCartByMember();
        cartRepository.delete(cart);
    }



    public Order createOrder(Order order, CartOrderDto cartOrderDto)   {
        Member member = memberService.findMember(cartOrderDto.getMemberId());

        List<CartItemDto> cartItemDtos = new ArrayList<>();

        orderCartItem(cartItemDtos, order);
        order.setStatus(OrderStatus.NOT_PAID); //주문상태
        order.setMember(member);
        order.makeOrderNumber(); //주문번호 만들기


        orderRepository.save(order);

        Long cartItemIdToRemove = cartOrderDto.getCartItemId();
        if (cartItemIdToRemove != null) {
            removeItem(cartItemIdToRemove);
        }

        return  order;
    }

    // 장바구니 상품 주문
    public void orderCartItem(List<CartItemDto> cartItemDtos ,Order order) {
        List<CartItem> cartItems = new ArrayList<>();

        cartItemDtos.stream().forEach(cartItemDto -> { //orderItems 리스트에 추가
            Item item = itemService.findItem(cartItemDto.getItemId());
            long quantity = cartItemDto.getCount();

            order.setCartItemsDto(new ArrayList<>());

            CartItem cartItem = new CartItem(quantity, item);
            cartItem.setName(item.getName());
            cartItem.setPrice(item.getPrice());


            cartItems.add(cartItem);
            });

        long totalPrice = getTotalPrice(cartItems);
        order.setTotalPrice(totalPrice);
        // 저장
        cartItemRepository.saveAll(cartItems);
    }


    public long getTotalPrice(List<CartItem> cartItems) {
        long price = 0;
        for (CartItem cartItem : cartItems) {
            price += cartItem.getPrice() * cartItem.getQuantity();
        }
        return price;
    }

    private void addItemInCart(Cart cart, CartItem cartItem) {
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItemRepository.save(cartItem));
        cart.setCartItems(cartItemList);
    }

}
