package com.mainproject.be28.domain.community.comment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mainproject.be28.domain.community.board.entity.Board;
import com.mainproject.be28.global.auditable.Auditable;
import com.mainproject.be28.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long commentId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Board_ID", nullable = false)
    @JsonBackReference
    private Board board;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Column(name = "CONTENT", length = 1000)
    private String content;

    @Column(name = "LIKE_COUNT")
    private Long likeCount;

}
