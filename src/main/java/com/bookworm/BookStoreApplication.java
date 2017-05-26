package com.bookworm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class that is used to bootstrap and launch a Spring application.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @since 1.7
 */
@SpringBootApplication
public class BookStoreApplication {

    /**
     * Starting point of the application. 
     * 
     * @param args are not used in this program.
     */
    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
        printCommands();
    }

    /**
     * Prints API endpoints to console at the start of the application.
     */
    private static void printCommands() {
        System.out.println("");
        System.out.println("");

        System.out.println("========== Backend Endpoints ==========");
        System.out.println("");

        System.out.println("===== AUTHENTICATION=====");
        System.out.println("POST /api/login");
        System.out.println("POST /api/register");
        System.out.println("");

        System.out.println("===== Book =====");
        System.out.println("GET /api/books");
        System.out.println("GET /api/books/{bookId}");
        System.out.println("GET /api/books/search/{searchWord}");
        System.out.println("GET /api/books/genre/{genre}");
        System.out.println("POST /api/books");
        System.out.println("DELETE /api/books/[bookId}");
        System.out.println("PUT /api/books/{bookId}/buy");
        System.out.println("PUT /api/books/{bookId}/add_stock");
        System.out.println("");

        System.out.println("===== Genres =====");
        System.out.println("GET /api/genres");
        System.out.println("");

        System.out.println("===== Publisher =====");
        System.out.println("GET /api/publishers");
        System.out.println("GET /api/publishers?search=SEARCH_TERM");
        System.out.println("");

        System.out.println("===== Author =====");
        System.out.println("GET /api/authors");
        System.out.println("GET /api/authors?search=SEARCH_TERM");
        System.out.println("");

        System.out.println("===== Review =====");
        System.out.println("GET /api/books/{bookId}/reviews");
        System.out.println("POST /api/books/{bookId}/reviews");
        System.out.println("");

        System.out.println("===== Notification =====");
        System.out.println("GET /api/books/{bookId}/notifications");
        System.out.println("POST /api/books/{bookId}/notifications");
        System.out.println("");
        
        System.out.println("===== Purchase =====");
        System.out.println("GET /api/purchases");
        System.out.println("GET /api/purchases/{purchaseId}");
        System.out.println("");
        
        System.out.println("===== MEMBER =====");
        System.out.println("GET /api/members");
        System.out.println("GET /api/members/{memberId}");
        System.out.println("");

        System.out.println("===== USE THESE USERS (username:password) =====");
        System.out.println("admin:admin");
        System.out.println("jeppe:jeppe");
        System.out.println("jussi:jussi");
        System.out.println("Emails:");
        System.out.println("admin - email: bookworm.mail.service@gmail.com, password: mailservice");
        System.out.println("jeppe - email: bookworm.mail.jeppe@gmail.com, password: mailservice");
        System.out.println("jussi - email: bookworm.mail.jussi@gmail.com, password: mailservice");
        
        System.out.println("========== END ==========");
    }
}
