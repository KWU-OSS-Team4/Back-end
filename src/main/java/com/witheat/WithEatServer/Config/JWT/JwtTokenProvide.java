package com.witheat.WithEatServer.Config.JWT;
import com.witheat.WithEatServer.Domain.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface JwtTokenProvide {
    JwtToken generateToken(Member member);
    Authentication getAuthentication(String accessToken);
    boolean validateToken(String token);
    void checkMemberId(Authentication authentication, HttpServletRequest request);
    void logoutToken(String accessToken);
    String resolveToken(HttpServletRequest request);
}