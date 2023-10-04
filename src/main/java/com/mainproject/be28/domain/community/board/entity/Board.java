package com.mainproject.be28.domain.community.board.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mainproject.be28.global.auditable.Auditable;
import com.mainproject.be28.domain.community.comment.entity.Comment;
import com.mainproject.be28.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import javax.persistence.*;

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
//    private Long memberId;

    @Column( length = 1000)
    private String content;

    @Column(columnDefinition = "bigint default 0")
    private Long viewCount = 0L;

    @Column(columnDefinition = "bigint default 0")
    private Long likeCount = 0L;

    @Column(nullable = false)
    private String boardCategory;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Comment> comments;
}
