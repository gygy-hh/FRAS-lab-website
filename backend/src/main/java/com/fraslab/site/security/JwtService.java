package com.fraslab.site.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expireSeconds;

    public JwtService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expire-seconds}") long expireSeconds) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.secretKey = Keys.hmacShaKeyFor(normalizeKey(keyBytes));
        this.expireSeconds = expireSeconds;
    }

    public String generate(String username) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expireSeconds)))
                .signWith(secretKey)
                .compact();
    }

    public String parseUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public long getExpireSeconds() {
        return expireSeconds;
    }

    private byte[] normalizeKey(byte[] keyBytes) {
        if (keyBytes.length >= 32) {
            return keyBytes;
        }
        byte[] normalized = new byte[32];
        System.arraycopy(keyBytes, 0, normalized, 0, keyBytes.length);
        for (int i = keyBytes.length; i < normalized.length; i++) {
            normalized[i] = '0';
        }
        return normalized;
    }
}
