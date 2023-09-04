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
public class Board extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long boardId;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Column( length = 1000)
    private String content;

    @Column(columnDefinition = "bigint default 0")
    private Long viewCount = 0L;

    @Column(columnDefinition = "bigint default 0")
    private Long likeCount = 0L;

    @Column(nullable = false)
    private String boardCategory;

}
