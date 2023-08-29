package com.mainproject.be28.itemImage.entity;

import com.mainproject.be28.item.entity.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long cartItemId;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String url;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;
}
