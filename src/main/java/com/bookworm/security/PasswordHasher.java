package com.bookworm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Handles creation and comparison of hashed passwords.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0522
 * @since 1.7
 */
public class PasswordHasher {

    /**
     * BCrypt encoder for hashing passwords.
     */
    @Autowired
    private static BCryptPasswordEncoder encoder;

    /**
     * Hashes the given password with BCrypt.
     *
     * @param pw The password to hash.
     * @return Hashed password.
     */
    public static String hashPassword(String pw) {
        encoder = new BCryptPasswordEncoder();
        return encoder.encode(pw);
    }

    /**
     * Compares a plaintext password to a hashed password.
     *
     * @param pw   Plaintext password.
     * @param hash Hashed password.
     * @return True if passwords matches the hash, false otherwise.
     */
    public static boolean compare(String pw, String hash) {
        return encoder.matches(pw, hash);
    }
}
