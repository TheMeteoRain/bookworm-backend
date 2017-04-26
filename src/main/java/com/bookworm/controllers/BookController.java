package com.bookworm.controllers;

import com.bookworm.models.*;
import com.bookworm.repositories.BookRepository;
import com.bookworm.repositories.PurchaseRepository;
import com.bookworm.security.AuthenticatedUser;
import validate.ValidationError;
import validate.ValidationErrorBuilder;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    protected final HttpServletRequest request; 
    
    @Autowired
    BookRepository bookRepository;
    
    @Autowired
    PurchaseRepository purchaseRepository;
    
    public BookController(HttpServletRequest request) {
        this.request = request;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Book> getBooks() {
        HttpHeaders headers = new HttpHeaders();
        Iterable<Book> books = bookRepository.findAll();
        
        if (books != null) {
            for (Book book : books) {
                Link selfLink = linkTo(BookController.class).slash(book.getBookId()).withSelfRel();
                Link buyLink = linkTo(methodOn(BookController.class).buyBook(book.getBookId(), 1)).withRel("buyBook");
                book.add(selfLink);
                book.add(buyLink);
            }
        }
        
        return new ResponseEntity(books, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
    public ResponseEntity<Book> getBook(@PathVariable long bookId) {
        HttpHeaders headers = new HttpHeaders();
        Book findThisBook = bookRepository.findOne(bookId);
        
        if (findThisBook != null) {
            Link selfLink = linkTo(BookController.class).slash(findThisBook.getBookId()).withSelfRel();
            Link buyLink = linkTo(methodOn(BookController.class).buyBook(findThisBook.getBookId(), 1)).withRel("buyBook");
            findThisBook.add(selfLink);
            findThisBook.add(buyLink);
        } else {
            findThisBook = new Book();
            Link allLink = linkTo(methodOn(BookController.class).getBooks()).withRel("allBooks");
            findThisBook.add(allLink);
        }
        
        return new ResponseEntity(findThisBook, headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Book> saveBook(@Validated @RequestBody Book c) {
        HttpHeaders headers = new HttpHeaders();
        Book saveThisBook = bookRepository.save(c);
        
        Link selfLink = linkTo(BookController.class).slash(saveThisBook.getBookId()).withSelfRel();
        Link allLink = linkTo(methodOn(BookController.class).getBooks()).withRel("allBooks");
        saveThisBook.add(selfLink);
        saveThisBook.add(allLink);
        headers.setLocation(linkTo(BookController.class).slash(saveThisBook.getBookId()).toUri());
        return new ResponseEntity(saveThisBook, headers, HttpStatus.CREATED);
    }
    
    @Transactional
    @RequestMapping(value="/{bookId}/buy", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Purchase> buyBook(@PathVariable long bookId, @RequestBody int amount) {
        AuthenticatedUser member = AuthenticatedUser.fromRequest(request);
        HttpHeaders headers = new HttpHeaders();
        Book buyThisBook = bookRepository.findOne(bookId);
        Purchase newPurchase = new Purchase();
        
        if (buyThisBook != null) {
            if (buyThisBook.getStock() - amount >= 0) {
                bookRepository.reduceStock(buyThisBook.getBookId(), amount);
                newPurchase = new Purchase(member.id, buyThisBook.getBookId(), amount);
                purchaseRepository.save(newPurchase);
            }
            
            headers.setLocation(linkTo(BookController.class).slash(buyThisBook.getBookId()).toUri());
        }
        
        return new ResponseEntity(newPurchase, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.DELETE)
    public void deleteBook(@PathVariable long bookId) {
        bookRepository.delete(bookId);
    }
    
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationError handleException(MethodArgumentNotValidException exception) {
        return createValidationError(exception);
    }

    private ValidationError createValidationError(MethodArgumentNotValidException e) {
        return ValidationErrorBuilder.fromBindingErrors(e.getBindingResult());
    }
}