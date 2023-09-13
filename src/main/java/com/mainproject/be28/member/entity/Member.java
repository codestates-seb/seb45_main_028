package com.mainproject.be28.member.entity;

import com.mainproject.be28.auditable.Auditable;
import com.mainproject.be28.member.dto.Stamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
 public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String address;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Stamp stamp;

    public Member(String email) {
        super();
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
        if (stamp.getMember() != this) {
            stamp.setMember(this);
        }

        // 회원 상태
//    @Column(nullable = false)
//    private MemberStatus status;
//
//    public enum MemberStatus {
//        ACTIVE,    // 활성 상태
//        SUSPENDED, // 정지 상태
//        INACTIVE,  // 비활성 상태
//        DELETED    // 삭제 상태
//    }

//
//   @Column()
//    private Long reportCount;

//   public Member(Long memberId); {
//        }

    }
}
