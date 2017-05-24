package com.bookworm.security;

/**
 * Contains users login credentials.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0522
 * @since 1.7
 */
public class LoginCredentials {

    /**
     * Username.
     */
    private String username;

    /**
     * Password.
     */
    private String password;

    /**
     * Empty constructor for Spring.
     */
    public LoginCredentials() {
    }

    /**
     * Retrieves the username.
     *
     * @return Username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the password.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Creates a string representation of this object.
     *
     * @return This object in string format.
     */
    @Override
    public String toString() {
        return "LoginCredentials{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
