package com.bookworm.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configures the applications security for routes.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0522
 * @since 1.7
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Sets up the configuration.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests()

                .antMatchers("/*", "/assets/**").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/api/books",
                        "/api/books/{\\d+}",
                        "/api/books/search/**",
                        "/api/books/genre/**",
                        "/api/genres",
                        "/api/publishers",
                        "/api/authors",
                        "/api/books/{\\d+}/reviews"
                ).permitAll()
                .antMatchers(HttpMethod.POST, "/login", "/register").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
