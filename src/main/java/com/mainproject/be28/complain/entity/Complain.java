package com.mainproject.be28.complain.entity;

import com.mainproject.be28.auditable.Auditable;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.*;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Complain extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long complainId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Item item;

    @Column(length = 1000)
    private String content;

    @Enumerated(EnumType.STRING)
    private ComplainStatus complainStatus;
    public enum ComplainStatus {
        COMPLAIN_NOT_EXIST("존재하지 않는 문의사항"),
        COMPLAIN_EXIST("존재하는 문의사항");

        @Getter
        private String status;
        ComplainStatus(String status) {
            this.status = status;
        }
    }

}
