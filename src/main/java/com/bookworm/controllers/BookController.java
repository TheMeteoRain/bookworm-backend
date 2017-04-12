package com.bookworm.controllers;

import com.bookworm.models.*;
import com.bookworm.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    BookRepository database;

    public BookController() {

    }

    @RequestMapping(method = RequestMethod.POST)
    public void saveBook(@RequestBody Book c) {
        database.save(c);
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.DELETE)
    public void deleteBook(@PathVariable long bookId) {
        database.delete(bookId);
    }

    @Transactional
    @RequestMapping(value = "/{bookId}/stock", method = RequestMethod.DELETE)
    public void reduceBookStock(@PathVariable long bookId) {
        database.reduceStock(bookId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Book> getBooks() {
        return database.findAll();
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
    public Book getBook(@PathVariable long bookId, HttpServletRequest req, HttpServletResponse res, Principal user) throws IOException {
        return database.findOne(bookId);
    }

    @RequestMapping("/example")
    public Book greeting() {
        return new Book(0, 1, "Java: A Beginners Guide", "Fully updated for Java Platform, Standard Edition 8 (Java SE 8)", "Java", "Hardcover", 20.89, 5, 728, "978-0071809252");
    }
}