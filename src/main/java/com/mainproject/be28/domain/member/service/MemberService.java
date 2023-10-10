package com.mainproject.be28.domain.member.service;


import com.mainproject.be28.domain.member.auth.userdetails.MemberAuthority;
import com.mainproject.be28.domain.member.dto.MemberPatchDto;
import com.mainproject.be28.domain.member.dto.PasswordPatchDto;
import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.member.mapper.MemberMapper;
import com.mainproject.be28.domain.member.repository.MemberRepository;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import com.mainproject.be28.global.utils.CustomBeanUtils;
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
    private final MemberMapper mapper;
    private final CustomBeanUtils<Member> beanUtils;

    //회원생성
    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        String encryptedPhone = passwordEncoder.encode(member.getPhone());
        String encryptedAddress = passwordEncoder.encode(member.getAddress());
        member.setPassword(encryptedPassword);
        member.setPhone(encryptedPhone);
        member.setAddress(encryptedAddress);
        member.setRoles(memberAuthority.createRoles(member.getEmail()));
//        isAdmin(member.getRoles());

        return memberRepository.save(member);
    }

    public void verifyAdmin(){
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
        return optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    private Optional<Member> checkUserExist(String email) {
        // 이메일을 사용하여 회원을 찾음

        // 회원이 존재하지 않을 경우 Optional.empty() 반환
        return memberRepository.findByEmail(email);
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
    public Member verifyEmailPassword(String email, String password) {
        Member currentMember = findTokenMember();
        String currentEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 현재 로그인되어 있는 회원의 메일
        Member member = memberRepository.findMemberByEmail(currentEmail).orElseThrow(() -> new BusinessLogicException(ExceptionCode.VERIFY_FAILURE));
        boolean matchMember = member.equals(currentMember);
        boolean matchEmail = email.equals(currentEmail); //실제 이메일과 입력한 이메일이 일치하는지
        boolean matchPassword = passwordEncoder.matches(password, member.getPassword());
        if(!matchMember||!matchEmail||!matchPassword){
            throw new BusinessLogicException(ExceptionCode.VERIFY_FAILURE); // 둘 중하나라도 다르다면 인증 실
        }
        return member;
    }

    public Member updateProfile(MemberPatchDto requestBody) {  // 주소, 핸드폰번호 변경.
        Member findMember = verifyEmailPassword(requestBody.getEmail(), requestBody.getPassword());
        Member member = mapper.memberPatchToMember(requestBody);

        Member updatedMember =
                beanUtils.copyNonNullProperties(member, findMember);

        return memberRepository.save(updatedMember);
    }

    public void changePassword(PasswordPatchDto passwordPatchDto) {
        Member findMember = verifyEmailPassword(passwordPatchDto.getEmail(), passwordPatchDto.getPassword());
       matchPassword(passwordPatchDto);
        String encodedPassword = passwordEncoder.encode(passwordPatchDto.getAfterPassword());
        findMember.setPassword(encodedPassword);
        memberRepository.save(findMember);
    }
    private static void matchPassword(PasswordPatchDto passwordPatchDto) {
        boolean matchNewPassword = passwordPatchDto.getAfterPassword().equals(passwordPatchDto.getConfirmPassword());
        if(!matchNewPassword){ throw new BusinessLogicException(ExceptionCode.DO_NOT_MATCH_PASSWORD);}
    }
    //회원 탈퇴
    public void deleteMember(String email, String password){
        Member member = verifyEmailPassword(email,password);
        memberRepository.delete(member);
    }
}