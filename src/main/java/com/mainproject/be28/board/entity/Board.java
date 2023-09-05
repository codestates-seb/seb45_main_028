package com.mainproject.be28.board.entity;

import com.mainproject.be28.auditable.Auditable;
import org.springframework.data.annotation.CreatedDate;
import com.mainproject.be28.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.security.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long boardId;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;
//    private Long memberId;

    @Column( length = 1000)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column()
    private Long viewCount;

    @Column()
    private Long likeCount;

    @Column(nullable = false)
    private String boardCategory;

}
