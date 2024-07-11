package com.sparta.workflowhelper.global.jwt;

import com.sparta.workflowhelper.global.common.enums.UserRole;
import com.sparta.workflowhelper.global.exception.customexceptions.TokenExpiredException;
import com.sparta.workflowhelper.global.exception.customexceptions.TokenInvalidException;
import com.sparta.workflowhelper.global.exception.errorcodes.TokenErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    public static final String ACCESS_HEADER = "Authorization";

    public static final String REFRESH_HEADER = "X-Refresh-Token";

    public static final String AUTHORIZATION_KEY = "auth";

    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret.key}")
    private String jwtSecret;
    private Key key;

    @Value("${jwt.access.expiration}")
    private int jwtAccessExpiration;
    
    @Value("${jwt.refresh.expiration}")
    private int jwtRefreshExpiration;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    @PostConstruct
    public void initKey() {
        byte[] bytes = Base64.getDecoder().decode(jwtSecret);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // JWT 토큰 생성
    public String createdAccessToken(String username, UserRole role) {
        Date date = new Date();
        
        return BEARER_PREFIX + Jwts.builder()
            .setSubject(username)
            .claim(AUTHORIZATION_KEY, role)
            .setIssuedAt(date)
            .setExpiration(new Date((date.getTime() + jwtAccessExpiration)))
            .signWith(key, signatureAlgorithm)
            .compact();
    }

    public String createdRefreshToken() {
        Date date = new Date();

        return BEARER_PREFIX + Jwts.builder()
            .setIssuedAt(date)
            .setExpiration(new Date((date.getTime() + jwtRefreshExpiration)))
            .signWith(key, signatureAlgorithm)
            .compact();
    }

    public String getJwtFromHeader(HttpServletRequest request, String headerName) {
        String token = request.getHeader(headerName);
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(7);
        }
        return null;
    }

    // JWT 토큰에서 사용자 이름 추출
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    // JWT 토큰의 유효성 검증
    public void checkJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new TokenInvalidException(TokenErrorCode.TOKEN_INVALID.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new TokenInvalidException(TokenErrorCode.TOKEN_INVALID.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new TokenExpiredException(TokenErrorCode.TOKEN_EXPIRED.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new TokenInvalidException(TokenErrorCode.TOKEN_INVALID.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new TokenInvalidException(TokenErrorCode.TOKEN_INVALID.getMessage());
        }
    }
}
