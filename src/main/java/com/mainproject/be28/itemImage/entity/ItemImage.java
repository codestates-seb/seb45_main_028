package com.mainproject.be28.itemImage.entity;

import com.mainproject.be28.auditable.Auditable;
import com.mainproject.be28.item.entity.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class ItemImage extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long itemImageId;

    @Column(length = 100)
    private String imageName;

    @Column
    private String originalName;

    @Column(length = 100)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;
    @Column
    private String RepresentationImage; // "Yes" , " No" 문자열로 구분
    public void updateItemImage(String originalName, String imageName, String url) {
        this.originalName = originalName;
        this.imageName = imageName;
        this.url = url;
    }
}
