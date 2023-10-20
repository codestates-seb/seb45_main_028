package com.mainproject.be28.domain.shopping.service;

import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.member.service.Layer2.MemberVerifyService;
import com.mainproject.be28.domain.shopping.cart.dto.CartDto;
import com.mainproject.be28.domain.shopping.cart.dto.CartItemDto;
import com.mainproject.be28.domain.shopping.cart.entity.Cart;
import com.mainproject.be28.domain.shopping.cart.service.CartService;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainPatchDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainPostDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponseDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponsesDto;
import com.mainproject.be28.domain.shopping.complain.service.ComplainService;
import com.mainproject.be28.domain.shopping.item.dto.ItemDto;
import com.mainproject.be28.domain.shopping.item.dto.ItemSearchConditionDto;
import com.mainproject.be28.domain.shopping.item.dto.OnlyItemResponseDto;
import com.mainproject.be28.domain.shopping.item.service.ItemService;
import com.mainproject.be28.domain.shopping.order.dto.CartOrderDto;
import com.mainproject.be28.domain.shopping.order.dto.OrderPostDto;
import com.mainproject.be28.domain.shopping.order.entity.Order;
import com.mainproject.be28.domain.shopping.order.service.OrderService;
import com.mainproject.be28.domain.shopping.review.dto.ReviewPostDto;
import com.mainproject.be28.domain.shopping.review.dto.ReviewResponseDto;
import com.mainproject.be28.domain.shopping.review.entity.Review;
import com.mainproject.be28.domain.shopping.review.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingServiceImpl implements ShoppingService {
    private final MemberVerifyService memberVerifyService;
    private final CartService cartService;
    private final ItemService itemService;
    private final OrderService orderService;
    private final ReviewService reviewService;
    private final ComplainService complainService;

    public ShoppingServiceImpl(MemberVerifyService memberVerifyService, CartService cartService, ItemService itemService, OrderService orderService, ReviewService reviewService, ComplainService complainService) {
        this.memberVerifyService = memberVerifyService;
        this.cartService = cartService;
        this.itemService = itemService;
        this.orderService = orderService;
        this.reviewService = reviewService;
        this.complainService = complainService;
    }

    @Override
    public ItemDto.Response findItem(long itemId) {
        return itemService.findItem(itemId);
    }

    @Override
    public Page<OnlyItemResponseDto> findItems(ItemSearchConditionDto condition) {
        return itemService.findItems(condition);
    }

    @Override
    public CartDto.Response addCart(CartItemDto cartItemDto) {

        return cartService.addCart(cartItemDto);
    }

    @Override
    public void removeItem(long itemId) {
        cartService.removeItem(itemId);
    }

    @Override
    public void removeAllItem() {
        cartService.removeAllItem();
    }

    @Override
    public Cart findCartByMember() {
        return cartService.findCartByMember();
    }

    @Override
    public Order createOrder(OrderPostDto orderPostDto) {
        return orderService.createOrder(orderPostDto);
    }

    @Override
    public Order findOrder() {
        return orderService.findOrder();
    }

    @Override
    public List<Order> getOrdersByDateToList() {
        return orderService.getOrdersByDateToList();
    }

    @Override
    public void cancelOrder(long itemId) {
        orderService.cancelOrder(itemId);
    }

    @Override
    public void deleteOrder() {
        orderService.deleteOrder();
    }

    @Override
    public Order createCartOrder(CartOrderDto cartOrderDto) {
        return orderService.createCartOrder(cartOrderDto);
    }

    @Override
    public Review createReview(ReviewPostDto reviewPostDto) {
        return reviewService.createReview(reviewPostDto, getMember());
    }
    @Override
    public Review updateReview(Review review) {
        return reviewService.updateReview(review);
    }

    @Override
    public Review findReview(long reviewId) {
        return reviewService.findReview(reviewId);
    }

    @Override
    public void deleteReview(long reviewId) {
        reviewService.deleteReview(reviewId);
    }

    @Override
    public Review addLike(Long reviewId) {
        return reviewService.addLike(reviewId);
    }

    @Override
    public Review addDislike(Long reviewId) {
        return reviewService.addDislike(reviewId);
    }

    @Override
    public Page<ReviewResponseDto> findReviewByMember(String name, int page, int size) {
        return reviewService.findReviewByMember(name, page, size);
    }

    @Override
    public ComplainResponseDto createComplain(ComplainPostDto complainPostDto) {
        return complainService.createComplain(complainPostDto, getMember());
    }

    @Override
    public ComplainResponseDto findComplain(Long complainId) {
        return complainService.findComplain(complainId);
    }

    @Override
    public ComplainResponseDto updateComplain(ComplainPatchDto complain) {
        return complainService.updateComplain(complain);
    }

    @Override
    public void deleteComplain(long complainId) {
        complainService.deleteComplain(complainId);
    }

    @Override
    public Page<ComplainResponsesDto> findComplains(int page, int size) {
        return complainService.findComplains(page, size);
    }

    @Override
    public Page<ComplainResponsesDto> findComplainsByMember(String name, int page, int size) {
        return complainService.findComplainsByMember(name, page, size);
    }

    private Member getMember(){
        return memberVerifyService.findTokenMember();
    }
}
