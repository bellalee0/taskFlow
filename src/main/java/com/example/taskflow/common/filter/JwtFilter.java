package com.example.taskflow.common.filter;

import com.example.taskflow.common.exception.CustomException;
import com.example.taskflow.common.exception.JwtExceptionHandler;
import com.example.taskflow.common.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.example.taskflow.common.exception.ErrorMessage.TOKEN_INVALID_FIELD;
import static com.example.taskflow.common.exception.ErrorMessage.TOKEN_REQUIRED_FIELD;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final JwtExceptionHandler jwtExceptionHandler;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURL = request.getRequestURI();

        if (requestURL.equals("/api/auth/login") || (requestURL.equals("/api/users") && "POST".equalsIgnoreCase(request.getMethod()))) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");

        try {
            if (token == null || token.isBlank()) {
                throw new CustomException(TOKEN_REQUIRED_FIELD);
            }

            if (!token.startsWith("Bearer ")) {
                throw new CustomException(TOKEN_REQUIRED_FIELD);
            }

            String jwt = token.substring(7);

            if (!jwtUtil.validateToken(jwt)) {
                throw new CustomException(TOKEN_INVALID_FIELD);
            }

            String username = jwtUtil.getUserName(jwt);

            User user = new User(username, "", List.of());

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));

            filterChain.doFilter(request, response);

        } catch (CustomException e) {
            jwtExceptionHandler.handleCustomException(response, e);
        }
    }
}
