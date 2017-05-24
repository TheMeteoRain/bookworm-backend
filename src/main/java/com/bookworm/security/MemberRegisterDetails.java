package com.bookworm.security;

/**
 * Contains information for registering a new member.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0522
 * @since 1.7
 */
public class MemberRegisterDetails {
    /**
     * Email to register with.
     */
    private String email;

    /**
     * Username to register with.
     */
    private String username;

    /**
     * Password to set for the member.
     */
    private String password;

    /**
     * Empty constructor for Spring.
     */
    public MemberRegisterDetails() {
    }

    /**
     * Creates an instance of {@link MemberRegisterDetails} with given values.
     *
     * @param email    Email.
     * @param username Username.
     * @param password Password.
     */
    public MemberRegisterDetails(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    /**
     * Retrieves the email from the member.
     *
     * @return Members email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the members email.
     *
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the password from the member.
     *
     * @return Members password.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the members username.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the password from the member.
     *
     * @return Members password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the members password.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
