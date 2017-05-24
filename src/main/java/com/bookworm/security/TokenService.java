package com.bookworm.security;

import com.bookworm.models.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Verifies and creates JWT tokens.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0522
 * @since 1.7
 */
public class TokenService {

    /**
     * Secret used as a JWT key.
     */
    private static final String JWT_KEY = "super_secret";

    /**
     * Creates a JWT token from {@link Member}
     *
     * @param member Member to create token for.
     * @return Created JWT token.
     */
    public static String createToken(Member member) {

        Claims claims = Jwts.claims().setSubject(member.getUsername());
        claims.put("id", member.getMemberId());
        claims.put("username", member.getUsername());
        claims.put("admin", member.isAdmin());
        return "Bearer " + Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, JWT_KEY)
                .compact();
    }

    /**
     * Checks if a token is valid.
     *
     * @param token Token to check.
     * @return True if token is valid, false otherwise.
     */
    public static AuthenticatedUser verify(String token) {
        if (token != null) {
            try {
                // Extract the token from "Bearer ..."
                token = token.substring(7);
                Jws<Claims> claims = Jwts.parser().setSigningKey(JWT_KEY).parseClaimsJws(token);
                int id = (int) claims.getBody().get("id");
                String username = (String) claims.getBody().get("username");
                boolean admin = (boolean) claims.getBody().get("admin");
                return new AuthenticatedUser(id, username, admin);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
