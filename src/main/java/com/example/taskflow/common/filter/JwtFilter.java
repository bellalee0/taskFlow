package com.example.taskflow.common.filter;

import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import static com.example.taskflow.common.exception.ErrorMessage.TOKEN_INVALID_FIELD;
import static com.example.taskflow.common.exception.ErrorMessage.TOKEN_REQUIRED_FIELD;



@Component
public class JwtFilter extends OncePerRequestFilter {
    // 속성
    private final JwtUtil jwtUtil;

    // 생성자
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // 기능
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 요청 url
        String requestURL = request.getRequestURI();

        // 로그인 url 일때 통과
        if (requestURL.equals("/api/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 헤더에 저장된 토큰
        String token = request.getHeader("Authorization");

        // 헤더에 토큰이 null이거나 공백일떄
        if (token == null || token.isBlank()) {
            throw new CustomException(TOKEN_REQUIRED_FIELD);
        }

        // 토큰 검증
        if (!jwtUtil.validateToken(token)) {
            throw new CustomException(TOKEN_INVALID_FIELD);
        }

        // 복호화 된 유저이름
        String username = jwtUtil.getUserName(token);

        // 인증/ 인가 된 유저
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                username, null, List.of());

        // 인증/ 인가 된 유저 객체를 시큐리티 컨택스트에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
