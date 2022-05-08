package com.irdaislakhuafa.alterraacademyfinalproject.security;

import java.util.*;
import java.util.function.Function;

import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

@Component
public class JwtUtility {
    @Value(value = "{app.key.secret}")
    private String SECRET;

    private final Long currentMillis = System.currentTimeMillis();
    private final Date CREATED_AT = new Date(currentMillis);
    private final Date EXPIRED_AT = new Date(currentMillis + (1000 * 60 * 60));

    public String generateTokenFromUser(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user.getId());
        claims.put("user_roles", user.getAuthorities());

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(CREATED_AT)
                .setExpiration(EXPIRED_AT)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        return token;
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    public <T> T getParticularClaimFromToken(String token, Function<Claims, T> resolver) {
        final Claims claims = this.getAllClaimsFromToken(token);
        return resolver.apply(claims);
    }

    public boolean isTokenExpired(String token) {
        return this.getParticularClaimFromToken(token, Claims::getIssuedAt).before(new Date());
    }

    public boolean isTokenValid(String token, User user) {
        final String userId = String.valueOf(this.getAllClaimsFromToken(token).get("user_id"));
        return (userId.equals(user.getId())) && (!this.isTokenExpired(token));
    }
}
