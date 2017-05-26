package com.bookworm.controllers;

import com.bookworm.models.Member;
import com.bookworm.repositories.MemberRepository;
import com.bookworm.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles authentication API endpoints.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0522
 * @since 1.7
 */
@RestController
@Scope("singleton")
@CrossOrigin(exposedHeaders = "Authorization")
@RequestMapping(value = "/api/members")
public class AuthController {

    /**
     * Member repository.
     */
    @Autowired
    MemberRepository memberRepository;

    /**
     * Default constructor for Spring.
     */
    public AuthController() {
    }

    /**
     * Logs in with the given credentials and gives a JWT token.
     *
     * @param credentials Login credentials object.
     * @param res         Servlet response object.
     * @return Response with a JWT token in headers if login successful.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody LoginCredentials credentials, HttpServletResponse res) {

        Member user = memberRepository.findFirstByUsernameOrEmail(credentials.getUsername(), credentials.getUsername());
        if (user != null) {
            boolean valid = PasswordHasher.compare(credentials.getPassword(), user.getPassword());
            if (valid) {
                if (user.getUsername().equals("admin")) {
                    user.setIsAdmin(true);
                }
                String token = TokenService.createToken(user);
                res.setHeader("Authorization", token);
                res.setStatus(200);
                return "Successfully logged in";
            }
        }
        res.setStatus(403);
        return "Login failed";
    }

    /**
     * Registers a new member.
     *
     * @param credentials Credentials to register with.
     * @return Message was registration successful.
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody MemberRegisterDetails credentials) {
        boolean created = registerMember(credentials);
        if (created) {
            return "Successfully registered a user";
        } else {
            return "Could not registered a user";
        }
    }

    /**
     * Tries to registers a user to the system.
     *
     * @param memberRegisterDetails User info to register with.
     * @return True if registration was successful, false otherwise.
     */
    public boolean registerMember(MemberRegisterDetails memberRegisterDetails) {
        Member member = new Member();
        member.setEmail(memberRegisterDetails.getEmail());
        member.setUsername(memberRegisterDetails.getUsername());
        String hash = PasswordHasher.hashPassword(memberRegisterDetails.getPassword());
        member.setPassword(hash);

        try {
            memberRepository.save(member);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Provides information about crrently logged in user.
     *
     * @param req Request used for getting the user.
     * @return User object as JSON.
     */
    @CrossOrigin
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<?> me(HttpServletRequest req) {
        AuthenticatedUser user = AuthenticatedUser.fromRequest(req);
        return ResponseEntity.ok(user);
    }
}