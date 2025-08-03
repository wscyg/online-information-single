package com.platform.auth.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret:your-very-long-secret-key-that-must-be-at-least-512-bits-long-for-hs512-algorithm-to-work-properly-this-should-be-long-enough}")
    private String secret;

    @Value("${jwt.expiration:604800}")
    private Long expiration;

    @Value("${jwt.refresh-expiration:2592000}")
    private Long refreshExpiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes();
        // 确保密钥长度至少为64字节（512位）
        if (keyBytes.length < 64) {
            // 如果密钥太短，用SHA-256哈希扩展到合适长度
            try {
                java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(keyBytes);
                // 重复哈希直到达到64字节
                byte[] expandedKey = new byte[64];
                for (int i = 0; i < 64; i++) {
                    expandedKey[i] = hash[i % hash.length];
                }
                return Keys.hmacShaKeyFor(expandedKey);
            } catch (Exception e) {
                throw new RuntimeException("Failed to generate signing key", e);
            }
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return createToken(claims, userId.toString(), expiration);
    }

    public String generateRefreshToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("type", "refresh");
        return createToken(claims, userId.toString(), refreshExpiration);
    }

    private String createToken(Map<String, Object> claims, String subject, Long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.get("userId", Long.class);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getExpiration() {
        return expiration;
    }
}