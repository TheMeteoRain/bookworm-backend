package com.bookworm.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

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
import java.net.URI;
import java.security.Principal;
import java.util.Arrays;
import javax.xml.ws.Response;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    BookRepository bookRepository;
    
    public BookController() {
        
    }

    @RequestMapping(method = RequestMethod.POST)
    public void saveBook(@RequestBody Book c) {
        bookRepository.save(c);
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.DELETE)
    public void deleteBook(@PathVariable long bookId) {
        bookRepository.delete(bookId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Book> getBooks() {
        HttpHeaders headers = new HttpHeaders();
        Iterable<Book> books = bookRepository.findAll();
        for (Book book : books) {
            Link selfLink = linkTo(BookController.class).slash(book.getBookId()).withSelfRel();
            Link buyLink = linkTo(methodOn(BookController.class).buyBook(book.getBookId())).withRel("Buy");
            book.add(selfLink);
            book.add(buyLink);
        }
        
        headers.setLocation(linkTo(BookController.class).toUri());
        
        return new ResponseEntity(books, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
    public ResponseEntity<Book> getBook(@PathVariable long bookId) {
        HttpHeaders headers = new HttpHeaders();
        Book book = bookRepository.findOne(bookId);
        Link selfLink = linkTo(BookController.class).slash(book.getBookId()).withSelfRel();
        Link buyLink = linkTo(methodOn(BookController.class).buyBook(book.getBookId())).withRel("Buy");
        book.add(selfLink);
        book.add(buyLink);
        
        headers.setLocation(linkTo(BookController.class).slash(book.getBookId()).toUri());
        
        return new ResponseEntity(book, headers, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value="/{bookId}/buy", method=RequestMethod.POST)
    public ResponseEntity<Book> buyBook(@PathVariable long bookId) {
        HttpHeaders headers = new HttpHeaders();
        Book book = bookRepository.findOne(bookId);

        if (book != null && book.getStock() > 0) {
            reduceBookStock(bookId);
        }
        headers.setLocation(linkTo(BookController.class).slash(book.getBookId()).toUri());
        
        return new ResponseEntity(bookRepository.findOne(bookId), headers, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/{bookId}/reduce_stock", method = RequestMethod.DELETE)
    public ResponseEntity<String> reduceBookStock(@PathVariable long bookId) {
        HttpHeaders headers = new HttpHeaders();
        Book book = bookRepository.findOne(bookId);
        bookRepository.reduceStock(book.getBookId());
        
        headers.setLocation(linkTo(BookController.class).slash(book.getBookId()).toUri());
        
        return new ResponseEntity("asd", headers, HttpStatus.OK);
    }
}