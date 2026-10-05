package org.zly.blogbackjava.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.miniapp-secret}")
    private String miniappJwtSecret;

    long EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000; // 7天过期时间

    private SecretKey getSigningKey(String secret) {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }


    public String generateToken(String id, String username) {
        // 用 生成 token
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME); // 7天过期时间
        return Jwts.builder()
                .claim("id", id)
                .claim("username", username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey(jwtSecret))
                .compact();
    }

    public String generateResetToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 5 * 60 * 1000);
        return Jwts.builder()
                .claim("email", email)
                .claim("type", "reset_password")
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey(jwtSecret))
                .compact();
    }

    public String generateMiniappAccessToken(String id, String openid) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 24L * 60 * 60 * 1000); // 1天过期时间
        return Jwts.builder()
                .claim("id", id)
                .claim("openid", openid)
                .claim("loginType", "miniapp")
                .claim("type", "access")
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey(miniappJwtSecret))
                .compact();
    }

    public String generateMiniappRefreshToken(String id, String openid) {
        // 30 天
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 30L * 24 * 60 * 60 * 1000); // 30天过期时间
        return Jwts.builder()
                .claim("id", id)
                .claim("openid", openid)
                .claim("loginType", "miniapp")
                .claim("type", "refresh")
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey(miniappJwtSecret))
                .compact();
    }

    public Claims parseToken(String token) {
        // 解析 token，返回 claims
        return Jwts.parser()
                .verifyWith(getSigningKey(jwtSecret))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Claims parseMiniappToken(String token) {
        // 解析 miniapp token，返回 claims
        return Jwts.parser()
                .verifyWith(getSigningKey(miniappJwtSecret))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
