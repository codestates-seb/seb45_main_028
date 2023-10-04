package com.mainproject.be28.domain.member.repository;

import com.mainproject.be28.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findMemberByEmail(String email);
}
