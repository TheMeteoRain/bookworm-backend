package com.bookworm.controllers;

import com.bookworm.models.Member;
import com.bookworm.repositories.MemberRepository;
import com.bookworm.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(exposedHeaders = "Authorization")
@RestController
public class AuthController {

    @Autowired
    MemberRepository database;

    public AuthController() {
        
    }

    @RequestMapping(value= "/login", method=RequestMethod.POST)
    public String login(@RequestBody LoginCredentials credentials, HttpServletResponse res) {

        Member user = database.findFirstByUsername(credentials.getUsername());
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

    @RequestMapping(value= "/register", method=RequestMethod.POST)
    public String register(@RequestBody MemberRegisterDetails credentials, HttpServletResponse res) {
        boolean created = registerMember(credentials);
        if (created) {
            return "Successfully registered a user";
        } else {
            return "Could not registered a user";
        }
    }

    public boolean registerMember(MemberRegisterDetails memberRegisterDetails) {
        Member member = new Member();
        member.setEmail(memberRegisterDetails.getEmail());
        member.setUsername(memberRegisterDetails.getUsername());
        String hash = PasswordHasher.hashPassword(memberRegisterDetails.getPassword());
        member.setPassword(hash);

        try {
            database.save(member);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @CrossOrigin
    @RequestMapping(value= "/me", method=RequestMethod.GET)
    public ResponseEntity<?> me(HttpServletRequest req) {
        AuthenticatedUser user = AuthenticatedUser.fromRequest(req);
        return ResponseEntity.ok(user);
    }
}