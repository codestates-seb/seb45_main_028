package com.mainproject.be28.order.entity;

import com.mainproject.be28.auditable.Auditable;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.order.data.OrderStatus;
import com.mainproject.be28.orderItem.entity.OrderItem;
import com.mainproject.be28.payment.entity.PayInfo;
import lombok.*;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;


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

    public void makeOrderNumber() {
        String date = ZonedDateTime.now(ZoneId.of("UTC")).plusHours(9).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        this.orderNumber = date + RandomStringUtils.randomNumeric(6);
    }

    public static Order createOrder(Member member) {
        Order order = new Order();
        order.setMember(member);

        return order;
    }



}
