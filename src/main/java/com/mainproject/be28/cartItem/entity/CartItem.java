package com.mainproject.be28.cartItem.entity;

import com.mainproject.be28.auditable.Auditable;
import com.mainproject.be28.cart.entity.Cart;
import com.mainproject.be28.item.entity.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class CartItem extends Auditable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column()
        private Long cartItemId;

        @Column(nullable = false)
        private Long count;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "CART_ID", nullable = false)
        private Cart cart;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "ITEM_ID", nullable = false)
        private Item item;

        public static CartItem createCartItem(Cart cart, Item item, long count) {
                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setItem(item);
                cartItem.setCount(count);
                return cartItem;
        }

        public void addCount(long count) {
                this.count += count;
        }

        public void updateCount(long count) {
                this.count = count;
        }
}
