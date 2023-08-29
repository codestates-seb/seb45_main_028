package com.mainproject.be28.item.entity;

import com.mainproject.be28.review.entity.Review;
import lombok.Getter;
import lombok.Setter;
import java.util.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(length = 100)
    private String itemName;

    @Column
    private Long itemPrice;

    @Column(length = 100)
    private String itemDetail;

    @Column(length = 100)
    private String itemStatus;

    @Column(length = 100)
    private String itemColor;

    @Column
    private Double itemScore;

    @Column(length = 100)
    private String itemBrand;

    @Column(length = 100)
    private String itemCategory;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();
}
