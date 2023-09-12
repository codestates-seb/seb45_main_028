package com.mainproject.be28.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
 public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String password;

    @Column( nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String address;

   @ElementCollection(fetch = FetchType.EAGER)
   private List<String> roles = new ArrayList<>();


    // 회원 상태
//    @Column(nullable = false)
//    private MemberStatus status;

    public enum MemberStatus {
        ACTIVE(0, "활성 상태"),   // 활성 상태
        SUSPENDED(1, "정지 상태"), // 정지 상태
        INACTIVE(2, "비활성 상태"),  // 비활성 상태
        DELETED(3, "삭제 상태");    // 삭제 상태
        private int code;
        private String status;
        MemberStatus(int code, String status) {
            this.code = code;
            this.status = status;
        }
    }

//
//   @Column()
//    private Long reportCount;

   public Member(Long memberId) {
   }
}
