package com.mainproject.be28.member.service;


import com.mainproject.be28.auth.userdetails.MemberAuthority;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberAuthority memberAuthority;


    //회원생성
    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        String encryptedPhone = passwordEncoder.encode(member.getPhone());
        String encryptedAdress = passwordEncoder.encode(member.getAddress());
        member.setPassword(encryptedPassword);
        member.setPhone(encryptedPhone);
        member.setAddress(encryptedAdress);
        member.setRoles(memberAuthority.createRoles(member.getEmail()));
//        isAdmin(member.getRoles());
        Member savedMember = memberRepository.save(member);

        return savedMember;
    }

    public void verifiyAdmin(){
        Member member = findTokenMember();
        List<String> roles = member.getRoles();
        for(String role : roles){
            if(role.equals("ADMIN")){return;}
        }
        throw new BusinessLogicException(ExceptionCode.ONLY_ADMIN_CAN);
    }

    // 회원이 존재하는지 검사 , 존재하면 예외
    private void verifyExistsEmail(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new BusinessLogicException(ExceptionCode.USER_EXIST);
        }
    }


    public Member findMember(Long memberId) {
        return findVerifiedMember(memberId);
    }

    public Member findVerifiedMember(Long memberId){
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);
        Member findMember =
                optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    private Optional<Member> checkUserExist(String email) {
        // 이메일을 사용하여 회원을 찾음
        Optional<Member> user = memberRepository.findByEmail(email);

        // 회원이 존재하지 않을 경우 Optional.empty() 반환
        return user;
    }

    public Long findTokenMemberId() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberRepository.findMemberByEmail(email).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return member.getMemberId();
    }
    public Member findTokenMember() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return memberRepository.findMemberByEmail(email).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)) ;
    }

    //현재 로그인한 회원정보 접근 시 회원 이메일/비밀번호 한번더 검증
    public void verifyEmailPassword(String email, String password) {
        //TODO: 로직 수정 필요
        String currentEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 현재 로그인되어 있는 회원의 메일
        Member member = memberRepository.findMemberByEmail(currentEmail).get(); //현재 회원정보

        String currentPassword = member.getPassword(); // 현재 로그인 되어있는 회원의 비밀번호
        String typedPassword = passwordEncoder.encode(password);
        boolean matchEmail = email.equals(currentEmail); //실제 이메일과 입력한 이메일이 일치하는지
        boolean matchPassword = typedPassword.equals(currentPassword); // 실제 비밀번호와 입력한 비밀번호가 일치하는지
        if(!matchEmail||!matchPassword){
            throw new BusinessLogicException(ExceptionCode.VERIFY_FAILURE); // 둘 중하나라도 다르다면 인증 실
        }
    }
}