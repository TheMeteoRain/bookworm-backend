package com.bookworm.security;

import javax.servlet.http.HttpServletRequest;

/**
 * Contains information about a user that has been authenticated.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0522
 * @since 1.7
 */
public class AuthenticatedUser {
    /**
     * User id.
     */
    public long id = 1;

    /**
     * Username.
     */
    public String username = "Teppo";

    /**
     * Tells if the user has admin privileges.
     */
    public boolean admin = false;

    /**
     * Creates an {@link AuthenticatedUser} with given parameters.
     *
     * @param id       User id.
     * @param username Username.
     * @param admin    True if should have admin privileges, false otherwise.
     */
    public AuthenticatedUser(long id, String username, boolean admin) {
        this.id = id;
        this.username = username;
        this.admin = admin;
    }

    /**
     * Creates an {@link AuthenticatedUser} from a JWT token in request.
     *
     * @param req The request to get to token from the headers.
     * @return The authenticated user.
     */
    public static AuthenticatedUser fromRequest(HttpServletRequest req) {
        return ((AuthenticatedUser) req.getAttribute("user"));
    }
}
