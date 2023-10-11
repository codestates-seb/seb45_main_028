package com.mainproject.be28.domain.shopping.item.entity;

import com.mainproject.be28.global.auditable.Auditable;
import com.mainproject.be28.domain.shopping.review.entity.Review;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity @Table
@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
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
    private Integer reviewCount;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ItemImage> Images;


}
