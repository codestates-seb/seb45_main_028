package com.mainproject.be28.auth.userdetails;

import com.mainproject.be28.auth.jwt.JwtTokenizer;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.member.entity.Member;
import com.mainproject.be28.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@Slf4j
public class UserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final MemberAuthority memberAuthority;
    private final JwtTokenizer jwtTokenizer;

    public UserDetailService(MemberRepository memberRepository, MemberAuthority memberAuthority, JwtTokenizer jwtTokenizer) {
        this.memberRepository = memberRepository;
        this.memberAuthority = memberAuthority;
        this.jwtTokenizer = jwtTokenizer;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Member member = memberRepository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//
//        // 사용자 정보를 UserDetails로 변환하여 반환 (예: org.springframework.security.core.userdetails.User)
//        return User.builder()
//                .username(member.getEmail())
//                .password(jwtTokenizer.encodedBase64SecretKey(member.getPassword()))
//                .roles("USER") // 사용자의 권한을 설정합니다.
//                .build();
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByEmail(username);
        Member findMember = optionalMember.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));


        return new MemberDetails(findMember);
    }
    public final class MemberDetails extends Member implements UserDetails {
        MemberDetails(Member member) {
            setMemberId(member.getMemberId());
            setEmail(member.getEmail());
            setPassword(member.getPassword());
            setName(member.getName());
            setAddress(member.getAddress());
            setPhone(member.getPhone());
            setRoles(member.getRoles());
        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}