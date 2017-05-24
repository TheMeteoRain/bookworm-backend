package com.bookworm;

import com.bookworm.controllers.AuthController;
import com.bookworm.security.MemberRegisterDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Loads data at the start up time of the application.
 * 
 * @version 2017.0522
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @since 1.7
 */
@Component
public class DataLoader implements ApplicationRunner{

    /**
     * Authentication controller.
     */
    private AuthController authController;

    /**
     * Constructs the instance.
     *
     * @param authController Handle to {@link AuthController}.
     */
    @Autowired
    public DataLoader(AuthController authController) {
        this.authController = authController;
    }

    /**
     * Gets called when the application starts to load data.
     *
     * @param applicationArguments Arguments for the application.
     * @throws Exception Exception thrown by Spring.
     */
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        authController.registerMember(new MemberRegisterDetails("bookworm.mail.service@gmail.com", "admin", "admin"));
        authController.registerMember(new MemberRegisterDetails("bookworm.mail.jeppe@gmail.com", "jeppe", "jeppe"));
        authController.registerMember(new MemberRegisterDetails("bookworm.mail.jussi@gmail.com", "jussi", "jussi"));
    }
}
