package com.sparta.workflowhelper.global.security;

import com.sparta.workflowhelper.global.exception.customexceptions.TokenExpiredException;
import com.sparta.workflowhelper.global.exception.customexceptions.TokenInvalidException;
import com.sparta.workflowhelper.global.exception.errorcodes.TokenErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.getWriter().write(TokenErrorCode.TOKEN_EXPIRED.getMessage());
        } catch (TokenInvalidException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
            response.getWriter().write(TokenErrorCode.TOKEN_INVALID.getMessage());
        }
    }
}
