package com.mainproject.be28.domain.member.service.Layer1;

import com.mainproject.be28.domain.member.auth.refresh.RefreshToken;
import com.mainproject.be28.domain.member.auth.refresh.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }
// 로그아웃 누르면 DB에서 refresh Token 삭제

    public void deleteRefreshToken(String username){
        RefreshToken refreshToken = refreshTokenRepository.findByUsername(username);
        refreshTokenRepository.delete(refreshToken);
    }
}