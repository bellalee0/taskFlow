package com.example.taskflow.common.utils;

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

    // key 초기화
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(stringSecretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String generationToken(String username) {
        // 토큰 발급 시간
        Date nowTokenDate = new Date();
        // 토큰 반환
        return Jwts.builder()
                .claim("username", username)
                .issuedAt(nowTokenDate)
                .expiration(new Date(nowTokenDate.getTime() + EXIT_TOKEN_TIME))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

}