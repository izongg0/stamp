package odin.stamp.common.authentication;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import odin.stamp.common.exception.JwtAuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {

    private final String secretKey;

    // bearer type
    private static final String BEARER_TYPE = "Bearer ";

    // header authorization key
    private static final String AUTHORIZATION_KEY = "Authorization";

    // accessToken 유효 시간
    private static final long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 60 * 2;

    // refreshToken 유효 시간
    private static final long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 30;

    public TokenProvider(@Value("${jwt.secretKey}") String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Access Token 발급
     * @param accountId
     * @param email
     * @return
     */
    public String generateAccessToken(Long accountId, String email, String name) {

        Claims claims = generateClaims(email, ACCESS_TOKEN_VALID_TIME);

        claims.put("id", accountId);
        claims.put("name", name);
        log.info("🔎 JWT Claims2222: {}", claims);  // ✅ JWT 내부 정보 확인용 로그

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
     * Token Resolve
     * @param request
     * @return
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_KEY);
        log.info("🛠 Authorization Header: {}", bearerToken); // ✅ JWT가 정상적으로 넘어오는지 확인

        if (bearerToken == null || !bearerToken.startsWith(BEARER_TYPE)) {
            throw new JwtAuthenticationException("authenticationFailed.account.token");
        }

        return bearerToken.substring(7);
    }

    /**
     * accessToken으로 authenticationInfo로 변환
     * @param accessToken
     * @return
     */
    public AuthenticationInfo getAuthenticationInfoByAccessToken(String accessToken) {
        Claims claims = Jwts.parserBuilder().
                setSigningKey(getSecretKey()).build().parseClaimsJws(accessToken).getBody();
        log.info("🔎 JWT Claims: {}", claims);  // ✅ JWT 내부 정보 확인용 로그

        return AuthenticationInfo.of(
                claims.get("id", Long.class),
                claims.getSubject(),
                claims.get("name", String.class)
        );
    }

    public String getEmailByRefreshToken(String refreshToken) {

        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(refreshToken)
                .getBody()
                .getSubject();

    }

    /**
     * 토큰 validation
     * @param token
     * @return
     */
    public Boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().
                    setSigningKey(getSecretKey()).build().parseClaimsJws(token);
            log.info("✅ JWT Token is valid");

            return !claims.getBody().getExpiration().before(new Date());

        } catch (JwtException | IllegalArgumentException ignored) {

        }
        return false;
    }

    /**
     * claims 생성
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

    /**
     * secret key 생성
     * @return
     */
    private SecretKey getSecretKey() {

        log.info("🔑 JWT Secret Key: {}", secretKey.getBytes(StandardCharsets.UTF_8));  // ✅ 키가 null이 아닌지 확인

        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
