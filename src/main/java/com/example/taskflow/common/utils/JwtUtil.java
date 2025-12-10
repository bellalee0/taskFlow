package com.example.taskflow.common.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    // 속성
    private static final long  EXIT_TOKEN_TIME = 60 * 60 * 1000L; // 60분

    @Value("${jwt.secret.key}")
    private String stringSecretKey;
    private SecretKey key;

    // 실행 후 제일 먼저 실행
    // key 초기화
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(stringSecretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String generationToken(String username) {
        Date nowTokenDate = new Date();
        return Jwts.builder()
                .claim("username", username)
                .issuedAt(nowTokenDate)
                .expiration(new Date(nowTokenDate.getTime() + EXIT_TOKEN_TIME))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    // 토큰 복호화
    public Claims decodeToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 복호화 된 토큰에서 유저정보 가져오기
    public String getUserName(String token) {
        Claims claims = decodeToken(token);
        return claims.get("username", String.class);
    }
}