package com.bookworm;

import com.bookworm.controllers.AuthController;
import com.bookworm.security.MemberRegisterDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 
 * 
 * @version 2017.0522
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @since 1.7
 */
@Component
public class DataLoader implements ApplicationRunner{

    private AuthController authController;

    @Autowired
    public DataLoader(AuthController authController) {
        this.authController = authController;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        authController.registerMember(new MemberRegisterDetails("bookworm.mail.service@gmail.com", "admin", "admin"));
        authController.registerMember(new MemberRegisterDetails("bookworm.mail.jeppe@gmail.com", "jeppe", "jeppe"));
        authController.registerMember(new MemberRegisterDetails("bookworm.mail.jussi@gmail.com", "jussi", "jussi"));
    }
}
