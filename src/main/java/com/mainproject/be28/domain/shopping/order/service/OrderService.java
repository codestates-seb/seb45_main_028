package com.mainproject.be28.domain.shopping.order.service;


import com.mainproject.be28.domain.shopping.order.dto.CartOrderDto;
import com.mainproject.be28.domain.shopping.order.dto.OrderPostDto;
import com.mainproject.be28.domain.shopping.order.entity.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
@Service
@Transactional
public interface OrderService  {

    Order createOrder(OrderPostDto orderPostDto);

    //주문아이디로 주문찾기
    Order findOrder() ;

    List<Order> getOrdersByDateToList();
    //주문취소(상태만 바꿈)
    void cancelOrder(long itemId);
    //데이터베이스에서 삭제
    void deleteOrder();

    Order createCartOrder(CartOrderDto cartOrderDto) ;
}
