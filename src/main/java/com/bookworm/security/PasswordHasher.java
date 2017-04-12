package com.bookworm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {

    @Autowired
    private static BCryptPasswordEncoder encoder;

    public static String hashPassword(String pw) {
        encoder = new BCryptPasswordEncoder();
        return encoder.encode(pw);
    }

    public static boolean compare(String pw, String hash) {
        return encoder.matches(pw, hash);
    }
}
