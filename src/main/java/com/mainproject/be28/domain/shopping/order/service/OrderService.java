package com.mainproject.be28.domain.shopping.order.service;


import com.mainproject.be28.domain.shopping.cart.entity.Cart;
import com.mainproject.be28.domain.shopping.cart.entity.CartItem;
import com.mainproject.be28.domain.shopping.cart.service.CartService;
import com.mainproject.be28.domain.shopping.order.data.OrderStatus;
import com.mainproject.be28.domain.shopping.order.dto.CartOrderDto;
import com.mainproject.be28.domain.shopping.order.dto.OrderPostDto;
import com.mainproject.be28.domain.shopping.order.entity.Order;
import com.mainproject.be28.domain.shopping.order.repository.OrderRepository;
import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.domain.shopping.item.repository.ItemRepository;
import com.mainproject.be28.domain.shopping.item.service.ItemService;
import com.mainproject.be28.domain.shopping.order.entity.OrderItem;
import com.mainproject.be28.domain.shopping.order.repository.OrderItemRepository;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
@Transactional
public class OrderService  {
    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final MemberService memberService;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;

    public Order createOrder(OrderPostDto orderPostDto)   {
        Member member = memberService.findTokenMember();
        Order order = new Order();
        order.makeOrderNumber();
        order.setMember(member);
        order.setStatus(OrderStatus.NOT_PAID);
        orderRepository.save(order);

        //주문이 완료되면 아이템 줄어듬
        decreaseItemStock(orderPostDto.getItemId(), orderPostDto.getQuantity());

        OrderItem orderItem= orderItemPostDtoToOrderItem(orderPostDto, order);
        List<OrderItem> items = new ArrayList<>();
        items.add(orderItem);
        order.setOrderItems(items);
        order.setTotalPrice(orderItem.getQuantity() * orderItem.getPrice());

        return order;
    }

        public OrderItem orderItemPostDtoToOrderItem(OrderPostDto orderPostDto,Order order) {
        Item item = itemService.verifyExistItem(orderPostDto.getItemId());
        long quantity = orderPostDto.getQuantity();

        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(item.getPrice());
        orderItem.setQuantity(quantity);
        orderItem.setOrder(order);
        orderItem.setItem(item);

        return orderItem;
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


    //주문취소(상태만 바꿈)
    public void cancelOrder(long itemId) {
        Order order = findOrder();
        OrderItem orderitem = orderItemRepository.findByItem_ItemId(itemId).orElseThrow(()->new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND));
        if (order != null) {
            order.setStatus(OrderStatus.ORDER_CANCELED);
            orderRepository.save(order);
        }
    }
    //데이터베이스에서 삭제
    public void deleteOrder() {
        Order order = findOrder();
        if (order != null) {
            orderRepository.delete(order);
        }
    }

    // 주문이 완료되면 아이템의 재고 수량을 감소시키는 메서드
    public void decreaseItemStock(long itemId, long quantity) {
        //아이템이 없으면 오류던짐
        Item item = itemRepository.findById(itemId).orElseThrow(() ->new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND));

        // 현재 재고 수량이 주문 수량보다 많아야 함을 확인
        if (item.getStock() >= quantity) {
            item.setStock((int) (item.getStock() - quantity));
            itemRepository.save(item);
        } else {
            throw new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND);//재고 부족 에러코드만들기

        }
    }


    @Transactional
    public Order createCartOrder(CartOrderDto cartOrderDto) {
        Member member = memberService.findTokenMember();
        Cart cart = cartService.findCartByMember();

        Order order =  Order.builder()
                .status(OrderStatus.NOT_PAID) // 주문 상태
                .member(member)
                .build();    //멤버정보

        order.makeOrderNumber(); // 주문 번호 만들기

        // 주문 항목 생성 및 저장
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
        // 주문 저장
        orderRepository.save(order);
        //주문항목 저장
        orderItemRepository.saveAll(orderItemList);
        //주문 후 아이템 재고 삭제
       decreaseItemStock(cartOrderDto.getCartItemId(), cartOrderDto.getCount());
        //장바구니 삭제?? -> 장바구니 안에 있는 장바구니상품만 삭제
        cartService.removeAllItem();
        return order;
    }
}
