package com.mainproject.be28.domain.member.service.Layer2;

import com.mainproject.be28.domain.member.auth.userdetails.MemberAuthority;
import com.mainproject.be28.domain.member.dto.PasswordPatchDto;
import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.member.repository.MemberRepository;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import com.mainproject.be28.global.utils.CustomBeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberVerifyService memberVerifyService;

    public ProfileServiceImpl(PasswordEncoder passwordEncoder, MemberRepository memberRepository, MemberVerifyService memberVerifyService) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
        this.memberVerifyService = memberVerifyService;
    }

    @Override
    public Member createMember(Member member) {
        memberVerifyService.verifyExistsEmail(member.getEmail());
        MemberAuthority memberAuthority = new MemberAuthority();

        encodePrivate(member);
        member.setRoles(memberAuthority.createRoles(member.getEmail()));
//        isAdmin(member.getRoles());

        return memberRepository.save(member);
    }

    @Override
    public Member updateProfile(Member member, String email, String password) {
        Member findMember = memberVerifyService.verifyEmailPassword(email, password);
        CustomBeanUtils<Member> beanUtils = new CustomBeanUtils<>();
        Member updatedMember =
                beanUtils.copyNonNullProperties(member, findMember);

        return memberRepository.save(updatedMember);
    }

    @Override
    public void changePassword(PasswordPatchDto passwordPatchDto) {
        Member findMember = memberVerifyService.verifyEmailPassword(passwordPatchDto.getEmail(), passwordPatchDto.getPassword());

        matchPassword(passwordPatchDto);

        String encodedPassword = passwordEncoder.encode(passwordPatchDto.getAfterPassword());
        findMember.setPassword(encodedPassword);
        memberRepository.save(findMember);
    }

    @Override
    public void deleteMember(String email, String password) {
        Member member = memberVerifyService.verifyEmailPassword(email,password);
        memberRepository.delete(member);
    }

    @Override
    public Member getProfile() {
        return memberVerifyService.findTokenMember();
    }

    private static void matchPassword(PasswordPatchDto passwordPatchDto) {
        boolean matchNewPassword = passwordPatchDto.getAfterPassword().equals(passwordPatchDto.getConfirmPassword());
        if(!matchNewPassword){ throw new BusinessLogicException(ExceptionCode.DO_NOT_MATCH_PASSWORD);}
    }
    private void encodePrivate(Member member) {
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        String encryptedPhone = passwordEncoder.encode(member.getPhone());
        String encryptedAddress = passwordEncoder.encode(member.getAddress());
        member.setPassword(encryptedPassword);
        member.setPhone(encryptedPhone);
        member.setAddress(encryptedAddress);
    }
}
