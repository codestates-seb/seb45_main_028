package com.mainproject.be28.member.repository;

import com.mainproject.be28.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {
}
