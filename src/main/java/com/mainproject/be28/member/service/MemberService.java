package com.mainproject.be28.member.service;

import com.mainproject.be28.member.controller.MemberController;
import com.mainproject.be28.member.dto.MemberPostDto;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.mapper.MemberMapper;
import com.mainproject.be28.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    //회원생성
    public Member createMember(Member member){
        String password = "{noop}" + member.getPassword();
        member.setPassword(password);
        return memberRepository.save(member);
    }


    //회원정보 수정
    public Member updateMember(Member member, Long memberId){
        Member findMember = findVerifiedMember(memberId);

        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> findMember.setPassword(password));
        Optional.ofNullable(member.getEmail())
                .ifPresent(email -> findMember.setEmail(email));
        Optional.ofNullable(member.getPhone())
                .ifPresent(phone -> findMember.setPhone(phone));
        Optional.ofNullable(member.getAddress())
                .ifPresent(address -> findMember.setAddress(address));

        Member saveMember = memberRepository.save(findMember);
        return saveMember;
    }



    //회원조회
    public Member findMember(Long memberId){
        Member findMember = findVerifiedMember(memberId);
        return findMember;
    }


//    public List<Member> findMembers() {
//        List<Member> members = memberRepository.findAll();
//        return members;
//    }


    public void deleteMember(Long memberId){
        Member member = findVerifiedMember(memberId);

        memberRepository.delete(member);
    }

    public Member findVerifiedMember(Long memberId){
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);
        Member findMember =
                optionalMember.orElseThrow();
        return findMember;
    }


}
