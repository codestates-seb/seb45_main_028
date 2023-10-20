package com.mainproject.be28.domain.shopping.order.entity;

import com.mainproject.be28.global.auditable.Auditable;
import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.shopping.order.data.OrderStatus;
import com.mainproject.be28.domain.shopping.payment.entity.PayInfo;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table (name = "orders")
public class Order extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Column(nullable = false, unique = true)
    private String orderNumber; // 주문 번호
    private Long totalPrice; // 주문 총액
    private OrderStatus status = OrderStatus.NOT_PAID; // 주문 상태


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Member_ID")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;


    @OneToOne(mappedBy = "order")
    private PayInfo payInfo;


    //===================================================//
    public void addMember(Member member) {
        this.member = member;
        if (!member.getOrder().contains(this)) {
            member.addOrder(this);
        }
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        if (orderItem.getOrder() != this) {
            orderItem.setOrder(this);
        }
    }

    public void addPayInfo(PayInfo payInfo) {
        this.payInfo = payInfo;
        if (payInfo.getOrder() != this) {
            payInfo.addOrder(this);
        }
    }



    public static Order createOrder(Member member) {
        Order order = new Order();
        order.setMember(member);

        return order;
    }



}
