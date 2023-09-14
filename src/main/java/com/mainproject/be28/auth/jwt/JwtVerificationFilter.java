package com.mainproject.be28.auth.jwt;

import com.mainproject.be28.auth.userdetails.MemberAuthority;
import com.mainproject.be28.auth.userdetails.UserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SignatureException;
import java.util.List;
import java.util.Map;

public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final MemberAuthority memberAuthority;
    private final UserDetailService userDetailService;

    public JwtVerificationFilter(JwtTokenizer jwtTokenizer,MemberAuthority memberAuthority, UserDetailService userDetailService) {
        this.jwtTokenizer = jwtTokenizer;
        this.memberAuthority = memberAuthority;
        this.userDetailService = userDetailService;
    }

    // 인가 과정 filter
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
        } catch (ExpiredJwtException expire) {
            request.setAttribute("exception", expire);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    // 토큰 값이 없으면 필터가 실행되지 않음
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer");
    }

    // 토큰 유효성 확인
    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", "");
        String base64EncodedSecretKey = jwtTokenizer.encodedBase64SecretKey(jwtTokenizer.getSecretKey());
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

        return claims;
    }

    // 토큰 유효성 확인 후 토큰 정보를 이용해서 이메일과 권한 정보가 있는 인증 토큰 생성 후 ContextHolder에 저장
    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("username");
        List<String> roles = (List<String>) claims.get("roles");
        List<GrantedAuthority> authorities = memberAuthority.createAuthority(roles);
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}