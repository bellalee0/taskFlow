package com.example.taskflow.common.filter;

import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import static com.example.taskflow.common.exception.ErrorMessage.TOKEN_INVALID_FIELD;
import static com.example.taskflow.common.exception.ErrorMessage.TOKEN_REQUIRED_FIELD;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    // 속성
    private final JwtUtil jwtUtil;

    // 기능
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURL = request.getRequestURI();

        if (requestURL.equals("/api/auth/login") || requestURL.equals("api/users")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");

        if (token == null || token.isBlank()) {
            throw new CustomException(TOKEN_REQUIRED_FIELD);
        }

        if (!jwtUtil.validateToken(token)) {
            throw new CustomException(TOKEN_INVALID_FIELD);
        }

        String username = jwtUtil.getUserName(token);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                username, null, List.of());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
