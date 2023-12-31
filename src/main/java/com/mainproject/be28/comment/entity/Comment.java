package com.mainproject.be28.comment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mainproject.be28.auditable.Auditable;
import com.mainproject.be28.board.entity.Board;
import com.mainproject.be28.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.time.LocalDateTime;

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
