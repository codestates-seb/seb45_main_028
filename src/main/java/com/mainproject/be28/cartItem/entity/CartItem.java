package com.mainproject.be28.cartItem.entity;

import com.mainproject.be28.cart.entity.Cart;
import com.mainproject.be28.item.entity.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class CartItem {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column()
        private Long cartItemId;

        @Column(nullable = false)
        private Long count;

        @ManyToOne
        @JoinColumn(name = "CART_ID", nullable = false)
        private Cart cart;

        @ManyToOne
        @JoinColumn(name = "ITEM_ID", nullable = false)
        private Item item;

}
