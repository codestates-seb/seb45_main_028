package com.mainproject.be28.message.entity;

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
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long messageId;

    @Column(name = "CONTENT", length = 1000)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
//    @JoinColumn(name = "MEMBER_ID",nullable = false, insertable = false, updatable = false)
    private Member senderMember;


    @ManyToOne
    @JoinColumn(name = "RESPONSE_MEMBER_ID", nullable = false)
    private Member responseMember;
}
