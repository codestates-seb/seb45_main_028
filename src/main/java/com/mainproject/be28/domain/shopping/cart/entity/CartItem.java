package com.mainproject.be28.domain.shopping.cart.entity;

import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.global.auditable.Auditable;
import lombok.*;

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

        public CartItem() {
        }

        public void addCount(long count) {
                this.count += count;
        }

}
