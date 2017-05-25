package com.bookworm.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;


/**
 * Verifies a JWT token to restrict access in API.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0522
 * @since 1.7
 */
public class JWTTokenFilter extends GenericFilterBean {

    /**
     * Filters requests to set authentication for Spring Security.
     *
     * @param servletRequest  Received request.
     * @param servletResponse Response to pass to next in chain.
     * @param filterChain     The filter chain to continue processing.
     * @throws IOException      Thrown by filterChain.
     * @throws ServletException Thrown by filterChain
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        String token = req.getHeader("Authorization");
        AuthenticatedUser user = TokenService.verify(token);
        if (user != null) {

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth);
            req.setAttribute("user", user);
        } else {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
