package com.witheat.WithEatServer.Config.JWT;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.witheat.WithEatServer.Exception.BaseException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@AllArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvide jwtTokenProvide;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, BaseException {
        String token = jwtTokenProvide.resolveToken((HttpServletRequest) request);

        try {
            // 토큰 유효성 검사
            if (token != null && jwtTokenProvide.validateToken(token)) {
                Authentication authentication = jwtTokenProvide.getAuthentication(token);
                jwtTokenProvide.checkMemberId(authentication, (HttpServletRequest) request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (BaseException baseException) {
            HttpServletResponse result = (HttpServletResponse) response;
            result.setStatus(baseException.getCode());
            result.setContentType("application/json;charset=UTF-8");
            result.getWriter().write("{\"status\":\"" + baseException.getCode() + "\",\"message\":\"" + baseException.getMessage() + "\"}");
            return;
        }

        chain.doFilter(request, response);
    }
}
