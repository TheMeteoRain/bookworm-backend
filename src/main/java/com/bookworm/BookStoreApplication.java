package com.bookworm;

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
        System.out.println("PUT /books/{bookId}/buy");
        System.out.println("PUT /books/{bookId}/add_stock");
        System.out.println("");

        System.out.println("===== Publisher =====");
        System.out.println("GET /publishers");
        System.out.println("GET /publishers?search=SEARCH_TERM");
        System.out.println("");


        System.out.println("===== Review =====");
        System.out.println("GET /books/{bookId}/reviews");
        System.out.println("POST /books/{bookId}/reviews");
        System.out.println("");

        System.out.println("===== Notification =====");
        System.out.println("GET /books/{bookId}/notifications");
        System.out.println("POST /books/{bookId}/notifications");
        System.out.println("");
        
        System.out.println("===== Purchase =====");
        System.out.println("GET /purchases");
        System.out.println("GET /purchases/{purchaseId}");
        System.out.println("");
        
        System.out.println("===== MEMBER =====");
        System.out.println("GET /members");
        System.out.println("GET /members/{memberId}");
        System.out.println("");

        System.out.println("===== USE THESE USERS (username:password | email:password) =====");
        System.out.println("admin:admin  | bookworm.mail.service@gmail.com:mailservice");
        System.out.println("jeppe:jeppe | bookworm.mail.jeppe@gmail.com:mailservice");
        System.out.println("jussi:jussi | bookworm.mail.jussi@gmail.com:mailservice");
        System.out.println("");
        
        System.out.println("========== END ==========");
    }
}
