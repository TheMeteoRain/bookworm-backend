package com.bookworm.security;

import javax.servlet.http.HttpServletRequest;

public class AuthenticatedUser {
    public long id = 1;
    public String username = "Teppo";
    public boolean admin = false;

    public AuthenticatedUser(long id, String username, boolean admin) {
        this.id = id;
        this.username = username;
        this.admin = admin;
    }

    public static AuthenticatedUser fromRequest(HttpServletRequest req) {
        return ((AuthenticatedUser) req.getAttribute("user"));
    }
}
