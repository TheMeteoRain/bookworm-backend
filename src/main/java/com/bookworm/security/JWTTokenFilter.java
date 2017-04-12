package com.bookworm.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


public class JWTTokenFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        String token = req.getHeader("Authorization");
        AuthenticatedUser user = TokenService.verify(token);
        if (user != null) {

            Authentication auth =  new UsernamePasswordAuthenticationToken(
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
