package odin.stamp.common.authentication;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import odin.stamp.common.exception.JwtAuthenticationException;
import odin.stamp.user.account.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class TokenProvider {

    private final String secretKey;

    // accessToken 유효 시간
    private static final long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 60 * 2;

    // refreshToken 유효 시간
    private static final long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 30;

    public TokenProvider(@Value("${jwt.secretKey}") String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Access Token 발급
     * @param account
     * @return
     */
    public String generateAccessToken(Account account) {
        Claims claims = generateClaims(account.getEmail(), ACCESS_TOKEN_VALID_TIME);

        claims.put("id", account.getId());
        claims.put("name", account.getName());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Refresh Token 발급
     * @param email
     * @return
     */
    public String generateRefreshToken(String email) {
        Claims claims = generateClaims(email, REFRESH_TOKEN_VALID_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * claims 생성
     * 사용자 정보, 토큰 발급시간, 토큰 만료 시간 등을 저장한 객체를 반환
     * @param subject
     * @param validTime
     * @return
     */
    private Claims generateClaims(String subject, long validTime) {

        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + validTime);
        return Jwts.claims()
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration);
    }

    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject(); // subject = email
    }

    /**
     * 토큰으로 사용자 정보를 만들어냄.
     * 옳은 사용자인지 확인하는 용도
     */
    public boolean validateToken(String token) {
        try {
            // 토큰을 파싱하여 Claims를 추출
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())  // 서명 검증을 위한 SecretKey
                    .build()
                    .parseClaimsJws(token);
            // 만료일 검사
            Date expiration = claims.getBody().getExpiration();
            if (expiration.before(new Date())) {
                return false;
            }

            return true;

        } catch (JwtException | IllegalArgumentException e) {
            // 토큰이 잘못되었거나, 만료되었거나, 서명이 맞지 않으면 예외 발생
            return false;
        }
    }

    public Claims getClaims(String token) {
        try {
            // JWT 토큰을 파싱하여 Claims를 추출
            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())  // 서명 검증을 위한 SecretKey
                    .build()
                    .parseClaimsJws(token)
                    .getBody();  // JWT에서 Claims 추출
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("Invalid or expired JWT token.");
        }
    }

    /**
     * secret key 생성
     * @return
     */
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}

