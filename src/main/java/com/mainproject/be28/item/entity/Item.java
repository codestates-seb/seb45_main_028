package com.mainproject.be28.item.entity;

import com.mainproject.be28.board.entity.Board;
import com.mainproject.be28.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column()
    private Long itemPrice;

    @Column(length = 100)
    private String itemDetail;

    @Column(length = 100)
    private String itemStatus;

    @Column(length = 100)
    private String itemColor;

    @Column()
    private Double itemScore;

    @Column(length = 100)
    private String itemBrand;

    @Column(length = 100)
    private String itemCategory;
}
