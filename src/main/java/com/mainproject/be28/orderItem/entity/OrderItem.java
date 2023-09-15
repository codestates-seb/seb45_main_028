package com.mainproject.be28.orderItem.entity;

import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.order.entity.Order;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;
    @Setter
    private Long quantity;
    @Setter
    private Long price;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;



    @Builder
    public OrderItem(long quantity, Item item) {
        this.quantity = quantity;
        this.item = item;
    }
    public void addOrder(Order order) {
        this.order = order;
        if(!order.getOrderItems().contains(this)) {
            order.addOrderItem(this);
        }
    }
    public static OrderItem createOrderItem(Item item, long quantity) {

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(item.getPrice());

        return orderItem;
    }


    public String setName(String name) {
        return item.getName();
    }
}
