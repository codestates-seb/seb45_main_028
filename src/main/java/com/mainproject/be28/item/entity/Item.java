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
    private String Name;

    @Column()
    private Long Price;

    @Column(length = 100)
    private String Detail;

    @Column(length = 100)
    private String Status;

    @Column(length = 100)
    private String Color;

    @Column()
    private Double Score;

    @Column(length = 100)
    private String Brand;

    @Column(length = 100)
    private String Category;
}
