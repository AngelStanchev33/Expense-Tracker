package com.angelstanchev.expense_tracker.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.angelstanchev.expense_tracker.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwstServiceImpl implements JwtService {

    private final String jwtSecret;
    private final long expiration;

    public JwstServiceImpl(
            @Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.expiration}") long expiration) {
        this.jwtSecret = jwtSecret;
        this.expiration = expiration;
    }

    @Override
    public String generateToken(String userEmail, Map<String, Object> claims) {

        if (userEmail == null || userEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        var now = new Date();
        return Jwts
                .builder()
                .claims(claims)
                .subject(userEmail)
                .issuedAt(now)
                .notBefore(now)
                .expiration(new Date(now.getTime() + expiration))
                .signWith(getSigningKey())
                .compact();

    }

    @Override
    public String generateToken(String userEmail) {

        return generateToken(userEmail, Map.of());
    }

    @Override
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    private Date getExpirationDate(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private <T> T extractClaims(String token, Function<Claims, T> cleamsResolver) {
        final Claims claims = extractAllClaims(token);
        return cleamsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
