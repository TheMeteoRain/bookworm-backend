package com.bookworm;

import com.bookworm.controllers.AuthController;
import com.bookworm.security.MemberRegisterDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner{

    private AuthController authController;

    @Autowired
    public DataLoader(AuthController authController) {
        this.authController = authController;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        authController.registerMember(new MemberRegisterDetails("admin", "admin", "admin"));
    }
}
