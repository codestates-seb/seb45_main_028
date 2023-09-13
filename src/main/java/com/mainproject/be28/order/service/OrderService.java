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
import com.mainproject.be28.order.dto.OrderPatchStatusDto;
import com.mainproject.be28.order.dto.OrderPostDto;
import com.mainproject.be28.order.dto.OrderRequestDto;
import com.mainproject.be28.order.entity.Order;
import com.mainproject.be28.order.mapper.OrderMapper;
import com.mainproject.be28.order.repository.OrderRepository;
import com.mainproject.be28.orderItem.dto.OrderItemPostDto;
import com.mainproject.be28.orderItem.entity.OrderItem;
import com.mainproject.be28.orderItem.repository.OrderItemRepository;
import com.mainproject.be28.utils.CustomBeanUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemService itemService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    // private final PaymentService paymentService;

    public Order createOrder(Order order, OrderPostDto orderPostDto, long memberId) throws IOException /*IamportResponseException,*/  {
        order.makeOrderNumber();

        Member member = memberService.findVerifiedMember(memberId);

        order.addMember(member);
        orderRepository.save(order);

        if (orderPostDto != null && orderPostDto.getOrderItems() != null) {
            OrderItemPostDtoToOrdersItem(orderPostDto.getOrderItems(), order);
            order.setTotalPrice(order.getOrderItems().stream()
                    .mapToLong(orderItem -> (orderItem.getPrice() * orderItem.getQuantity()))
                    .sum());
        } else {
            Optional<List<OrderItemPostDto>> orderItemsOptional = Optional.ofNullable(orderPostDto.getOrderItems());
            if (!orderItemsOptional.isPresent()) {
                throw new BusinessLogicException(ExceptionCode.ORDER_ITME_NO_FOUND );
            }
            // 주문 아이템이나 주문 정보가 유효하지 않은 경우 예외 처리 또는 기본 로직 추가
            throw new BusinessLogicException(ExceptionCode.INVALID_ORDER_DATA);
        }
        // paymentService.postPrepare(order.getOrderNumber(), order.getTotalPrice());
        return order;
    }

    //// ItemId 와 quantity, member로 OrdersItem를 저장
    private void OrderItemPostDtoToOrdersItem(List<OrderItemPostDto> orderItemPostDtos, Order order) {
        orderItemPostDtos.stream().forEach(orderItemPostDto -> {
            Item item = itemService.findItem(orderItemPostDto.getItemId());
            int quantity = orderItemPostDto.getQuantity();

            OrderItem orderItem = new OrderItem(quantity, item);
            orderItem.addOrder(order);
            orderItem.setName(item.getName());
            orderItem.setPrice(item.getPrice());
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            order.getOrderItems().add(orderItem); // 주문 아이템을 주문에 직접 추가
        });
    }
    //주문번호로 주문찾기
    public Order findByOrderNumber(String orderNumber) {
        Optional<Order> order = orderRepository.findByOrderNumber(orderNumber);
        return order.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
    }
    //주문아이디로 주문찾기
    public Order findOrder(long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
    }
    // 로그인한 유저가 주문자인지 확인
    public void checkOrderHolder(Order order, long memberId) {
        long orderHolderId = order.getMember().getMemberId();
        if (orderHolderId != memberId) {
            throw new BusinessLogicException(ExceptionCode.NOT_ORDER_HOLDER);
        }
    }
    public List<Order> getOrdersByDateToList(long memberId) {
        Member member = memberService.findVerifiedMember(memberId);
        return orderRepository.findByMemberOrderByCreatedAtDesc(member);// creat_At을 기준으로 내림차순으로
    }
    //주문번호 확인
    public void checkOrderHolder(String orderNumber, long userId) {
        long orderHolderId = findByOrderNumber(orderNumber).getMember().getMemberId();
        if(orderHolderId != userId) {
            throw new BusinessLogicException(ExceptionCode.NOT_ORDER_HOLDER);
        }
    }
    //주문취소
    public void cancelOrder(String orderNumber) {
        Order order = findByOrderNumber(orderNumber);
        int index = order.getStatus().getIndex();
        if (index == 4 || index == 3) {
            order.applyRefund();
        } else if (index == 1) {
            // 주문 취소 메서드 필요 (아임포트)
            order.cancelOrder();
        } else if (index == 5) {
            throw new BusinessLogicException(ExceptionCode.ALREADY_APPLIED_REFUND);
        } else if (index == 2) {
            throw new BusinessLogicException(ExceptionCode.ALREADY_CANCELED);
        } else {
            orderRepository.delete(order);
        }
    }
    //주문 처리 중에 오류가 발생한 경우
    public void errorWhilePaying(Order order) {
        order.errorWhilePaying();
        orderRepository.save(order);
    }

    //주문 결제
    public void paidOrder(Order order) {
        order.paid();
        orderRepository.save(order);
    }


    public void changeStatus(String orderNumber, OrderPatchStatusDto dto) {
        Order order = findByOrderNumber(orderNumber);
        order.setStatus(OrderStatus.valueOfStatus(dto.getStatus()));
        orderRepository.save(order);
    }
}
