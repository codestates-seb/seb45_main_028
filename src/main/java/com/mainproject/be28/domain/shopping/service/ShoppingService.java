package com.mainproject.be28.domain.shopping.service;

import com.mainproject.be28.domain.shopping.cart.dto.CartDto;
import com.mainproject.be28.domain.shopping.cart.dto.CartItemDto;
import com.mainproject.be28.domain.shopping.cart.entity.Cart;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainPatchDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainPostDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponseDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponsesDto;
import com.mainproject.be28.domain.shopping.item.dto.ItemDto;
import com.mainproject.be28.domain.shopping.item.dto.ItemSearchConditionDto;
import com.mainproject.be28.domain.shopping.item.dto.OnlyItemResponseDto;
import com.mainproject.be28.domain.shopping.order.dto.CartOrderDto;
import com.mainproject.be28.domain.shopping.order.dto.OrderPostDto;
import com.mainproject.be28.domain.shopping.order.entity.Order;
import com.mainproject.be28.domain.shopping.review.dto.ReviewPostDto;
import com.mainproject.be28.domain.shopping.review.dto.ReviewResponseDto;
import com.mainproject.be28.domain.shopping.review.entity.Review;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ShoppingService {
    //todo: 인터페이스 적용? 미적용?
//    <T> T creat(T postDto);
//    <T> T read(long id);
//    <T> T update(T patchDto, long id);
//    void delete(long id);
//    void deleteAll();
//    <T> Page<T> readAll(Class<T> classType, int page, int size);
//    <T> Page<T> readAll(ItemSearchConditionDto condition);
//    <T> Page<T> readAllByMember(Class<T> classType, String name, int page, int size);
//

    //item
    ItemDto.Response findItem(long itemId);
    Page<OnlyItemResponseDto> findItems(ItemSearchConditionDto condition);
    // Cart
    CartDto.Response addCart(CartItemDto cartItemDto);
    void removeItem(long itemId);
    void removeAllItem();
    Cart findCartByMember();

    // Order
    Order createOrder(OrderPostDto orderPostDto);
    Order findOrder();
    List<Order> getOrdersByDateToList();
    void deleteOrder();
    void cancelOrder(long itemId);

    Order createCartOrder(CartOrderDto cartOrderDto);

    // Review
    Review createReview(ReviewPostDto reviewPostDto);
    Review updateReview(Review review);
    Review findReview(long reviewId);
    void deleteReview(long reviewId);
    Review addLike(Long reviewId) ;
    Review addDislike(Long reviewId) ;
    Page<ReviewResponseDto> findReviewByMember(String name, int page, int size) ;

    //Complain
    ComplainResponseDto createComplain(ComplainPostDto complainPostDto);
    ComplainResponseDto findComplain(Long complainId);
    ComplainResponseDto updateComplain(ComplainPatchDto complain);
    void deleteComplain(long complainId);
    Page<ComplainResponsesDto> findComplains(int page, int size);
    Page<ComplainResponsesDto> findComplainsByMember(String name, int page, int size);


}

