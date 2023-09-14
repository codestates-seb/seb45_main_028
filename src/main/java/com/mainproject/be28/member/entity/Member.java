package com.mainproject.be28.member.entity;

import com.mainproject.be28.order.entity.Order;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table
 public class Member{
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

    public Member(String email) {
        this.email = email;
    }


    public Member() {

    }


    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Order> order = new ArrayList();

    public void addOrder(Order order) {
        this.order.add(order);
        if (order.getMember() != this) {
            order.addMember(this);
        }
    }
    public boolean isSameMemberId(long memberId){
           return this.memberId == memberId;
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
