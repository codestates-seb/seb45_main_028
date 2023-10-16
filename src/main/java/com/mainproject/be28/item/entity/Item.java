package com.mainproject.be28.item.entity;

import com.mainproject.be28.auditable.Auditable;
import com.mainproject.be28.itemImage.entity.ItemImage;
import com.mainproject.be28.review.entity.Review;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table
public class Item extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(length = 100)
    private String name;

    @Column
    private Long price;

    @Column(length = 100)
    private String detail;

    @Column(length = 100)
    private Integer stock;

    @Column(length = 100)
    private String color;

    @Column(length = 100)
    private String brand;

    @Column(length = 100)
    private String category;

    @Transient
    private Double score;

    @Transient
    private Long reviewCount;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ItemImage> Images = new ArrayList<>();


}
