package com.mainproject.be28.domain.member.auth.userdetails;


import java.util.Collection;
import java.util.Map;

import com.mainproject.be28.domain.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class PrincipalDetails extends Member implements UserDetails {
    private final  MemberAuthority authorityUtils;
    private Map<String, Object> attributes;

    public PrincipalDetails(Member member,  MemberAuthority authorityUtils) {

        this.authorityUtils = authorityUtils;
    }

    public PrincipalDetails(Member member, MemberAuthority authorityUtils, Map<String, Object> attributes) {

        this.authorityUtils = authorityUtils;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityUtils.createAuthority(this.getRoles());
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
