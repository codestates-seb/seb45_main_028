package com.mainproject.be28.payment.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.mainproject.be28.order.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PayInfo {

    public PayInfo(String impUid) {
        this.impUid = impUid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String impUid;

    public void addOrder(Order order) {
        this.order = order;
        if(order.getPayInfo() != this) {
            order.addPayInfo(this);
        }
    }
}
