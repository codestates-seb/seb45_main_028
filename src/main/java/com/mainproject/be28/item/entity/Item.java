package com.mainproject.be28.item.entity;

import com.mainproject.be28.auditable.Auditable;
import com.mainproject.be28.review.entity.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import javax.persistence.*;

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
    private String status;

    @Column(length = 100)
    private String color;

    @Column(length = 100)
    private String brand;

    @Column(length = 100)
    private String category;

    @Transient
    private Double score;

    @Transient
    private long reviewCount;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();
}
