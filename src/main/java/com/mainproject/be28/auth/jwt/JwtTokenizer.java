package com.mainproject.be28.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static java.util.Calendar.MINUTE;

@Component
public class JwtTokenizer {

    @Getter
    private String secretKey = "rodnfrkdpdhfcoddlgksakflRHanfRHanfgodjaclekdkvekflrkTNrenltekflrkTNrvkfWKrvkfWKrornflehoTsp";

    @Getter
    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Getter
    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;

    public String encodedBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //jwt 생성
    public String generateAccessToken(Map<String, Object> claims,
                                      String subject,
                                      Date expiration,
                                      String encodedBase64SecretKey) {

        Key key = getKeyFromBase64SecretKey(encodedBase64SecretKey);

        return Jwts.builder()
                .setClaims(claims)//인증된 사용자와 관련된 정보 추가
                .setSubject(subject)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String subject,
                                       Date expiration,
                                       String encodedBase64SecretKey) {

        Key key = getKeyFromBase64SecretKey(encodedBase64SecretKey);

        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(MINUTE, expirationMinutes);

        Date expiration = calendar.getTime();

        return expiration;

    }

    // 검증 후, Claims을 반환하는 용도
    public Jws<Claims> getClaims(String jws, String encodedBase64SecretKey) {
        Key key = getKeyFromBase64SecretKey(encodedBase64SecretKey);

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    // 단순히 검증만 하는 용도로 쓰일 경우
    public void verifySignature(String jws, String encodedBase64SecretKey) {
        Key key = getKeyFromBase64SecretKey(encodedBase64SecretKey);

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    //secret Key 생성
    private Key getKeyFromBase64SecretKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        // Ensure the key size is at least 256 bits (32 bytes)
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("Key size must be at least 256 bits");
        }

        Key key = Keys.hmacShaKeyFor(Arrays.copyOf(keyBytes, 32)); // Truncate to 256 bits

        return key;

    }

}
