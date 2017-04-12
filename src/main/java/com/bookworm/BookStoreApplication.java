package com.bookworm;

import com.bookworm.controllers.AuthController;
import com.bookworm.security.MemberRegisterDetails;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
                printCommands();
	}

    private static void printCommands() {
        System.out.println("");
        System.out.println("");

        System.out.println("========== Backend Commands ==========");
        System.out.println("");

        System.out.println("===== AUTHENTICATION=====");
        System.out.println("POST /login");
        System.out.println("POST /register");
        System.out.println("GET /me");
        System.out.println("");

        System.out.println("===== Book =====");
        System.out.println("GET /books");
        System.out.println("GET /books/{bookId}");
        System.out.println("POST /books");
        System.out.println("DELETE /books/[bookId}");
        System.out.println("DELETE /books/{bookId}/stock");
        System.out.println("");
        
        System.out.println("===== MEMBER =====");
        System.out.println("GET /members");
        System.out.println("GET /members/{memberId}");
        System.out.println("TEHKÄÄ JOTAIN TÄHÄN TYYLIIN");
        System.out.println("");
        
        System.out.println("===== USE THESE USERS (username:password) =====");
        System.out.println("admin:admin");
        System.out.println("jeppe:jeppe");
        System.out.println("jussi:jussi");
        System.out.println("");
        
        System.out.println("========== END ==========");
    }
}
