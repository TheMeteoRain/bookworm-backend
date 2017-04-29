package com.bookworm.security;

import com.bookworm.models.Member;
import io.jsonwebtoken.*;

public class TokenService {

    private static final String JWT_KEY = "super_secret";

    public static String createToken(Member member) {

        Claims claims = Jwts.claims().setSubject(member.getUsername());
        claims.put("id", member.getMemberId());
        claims.put("username", member.getUsername());
        claims.put("admin", member.isAdmin());
        return "Bearer " +  Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, JWT_KEY)
                .compact();
    }

    public static AuthenticatedUser verify(String token) {
        if (token != null) {
            try {
                // Extract the token from "Bearer ..."
                token = token.substring(7);
                Jws<Claims> claims = Jwts.parser().setSigningKey(JWT_KEY).parseClaimsJws(token);
                int id = (int) claims.getBody().get("id");
                String username = (String)claims.getBody().get("username");
                boolean admin = (boolean)claims.getBody().get("admin");
                return new AuthenticatedUser(id, username, admin);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
