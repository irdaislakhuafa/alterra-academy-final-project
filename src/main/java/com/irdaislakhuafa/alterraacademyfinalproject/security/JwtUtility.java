package com.irdaislakhuafa.alterraacademyfinalproject.security;

import java.util.HashMap;
import java.util.Map;

import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.User;

import org.springframework.stereotype.Component;

@Component
public class JwtUtility {
    public String generateTokenFromUser(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user.getId());
        claims.put("user_roles", user.getAuthorities());

        // String token = Jwt
        return "";
    }
}
