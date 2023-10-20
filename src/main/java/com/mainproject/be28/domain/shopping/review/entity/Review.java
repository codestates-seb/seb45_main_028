package com.mainproject.be28.domain.shopping.review.entity;

import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.global.auditable.Auditable;
import com.mainproject.be28.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table @Builder
public class Review extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Column(length = 1000)
    private String content;

    @Column
    private Long  likeCount= 0L;

    @Column
    private Long unlikeCount=0L;

    @Column()
    private int Score;

    //
    public static Review createReview(Member member) {
        Review review = new Review();
        review.setMember(member);

        return review;
    }

}
