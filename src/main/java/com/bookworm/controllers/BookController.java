package com.bookworm.controllers;

import com.bookworm.models.*;
import com.bookworm.repositories.AuthorRepository;
import com.bookworm.repositories.BookRepository;
import com.bookworm.repositories.MemberRepository;
import com.bookworm.repositories.PublisherRepository;
import com.bookworm.repositories.PurchaseRepository;
import com.bookworm.security.AuthenticatedUser;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import validate.ValidationError;
import validate.ValidationErrorBuilder;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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

@Scope("singleton")
@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Resource
    private HttpServletRequest request;
    
    @Autowired
    BookRepository bookRepository;
    
    @Autowired
    PurchaseRepository purchaseRepository;
    
    @Autowired
    MemberRepository memberRepository;
    
    @Autowired
    AuthorRepository authorRepository;
    
    @Autowired
    PublisherRepository publisherRepository;
    
    public BookController() {
        
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBooks() {
        Iterable<Book> books = bookRepository.findAll();
        ResponseEntity<Book> response = new ResponseEntity(HttpStatus.NOT_FOUND);
        
        if (books != null) {
            response = new ResponseEntity(books, HttpStatus.OK);
            
            for (Book book : books) {
                Link selfLink = linkTo(BookController.class).slash(book.getBookId()).withSelfRel();
                Link buyLink = linkTo(methodOn(BookController.class).buyBook(book.getBookId(), 0)).withRel("buyBook");
                Link reviewLink = linkTo(methodOn(ReviewController.class).saveReview(book.getBookId(), null)).withRel("saveReview");
                book.add(selfLink);
                book.add(buyLink);
                book.add(reviewLink);
            }
        }
        
        return response;
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBook(@PathVariable long bookId) {
        Book findThisBook = bookRepository.findOne(bookId);
        ResponseEntity<Book> response = new ResponseEntity(HttpStatus.NOT_FOUND);
        
        if (findThisBook != null) {
            response = new ResponseEntity(findThisBook, HttpStatus.OK);
            
            Link selfLink = linkTo(BookController.class).slash(findThisBook.getBookId()).withSelfRel();
            Link buyLink = linkTo(methodOn(BookController.class).buyBook(findThisBook.getBookId(), 0)).withRel("buyBook");
            findThisBook.add(selfLink);
            findThisBook.add(buyLink);
        }
        
        return response;
    }
    
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> saveBook(@Validated @RequestBody Book book) {
        HttpHeaders headers = new HttpHeaders();
        
        List<Author> authors;
        if ((authors = book.getAuthors()).size() > 0) {
            List<Author> newAuthors = new ArrayList<>();
            for (Author author : authors) {
                Author findAuthor = authorRepository.findOne(author.getAuthorId());
                newAuthors.add(findAuthor);
            }
            book.setAuthors(newAuthors);
        }
        
        Publisher publisher;
        if ((publisher = publisherRepository.findOne(book.getPublisher().getPublisherId())) != null) {
            book.setPublisher(publisher);
        }
        
        Book saveThisBook = bookRepository.save(book);
        
        Link selfLink = linkTo(BookController.class).slash(saveThisBook.getBookId()).withSelfRel();
        Link allBooks = linkTo(methodOn(BookController.class).getBooks()).withRel("allBooks");
        saveThisBook.add(selfLink);
        saveThisBook.add(allBooks);
        
        headers.setLocation(linkTo(BookController.class).slash(saveThisBook.getBookId()).toUri());
        
        return new ResponseEntity(saveThisBook, headers, HttpStatus.CREATED);
    }
    
    @Transactional
    @RequestMapping(value="/{bookId}/buy", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Purchase> buyBook(@PathVariable long bookId, @RequestBody int amount) {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Purchase> response = new ResponseEntity(HttpStatus.NOT_FOUND);
        Member findThisMember = memberRepository.findOne(AuthenticatedUser.fromRequest(request).id);
        Book buyThisBook = bookRepository.findOne(bookId);
        
        if (buyThisBook != null) {
            Purchase newPurchase = new Purchase(findThisMember, buyThisBook, amount);
            headers.setLocation(linkTo(PurchaseController.class).slash(newPurchase.getPurchaseId()).toUri());
            
            if (buyThisBook.getStock() - amount >= 0) {
                response = new ResponseEntity(newPurchase, headers, HttpStatus.CREATED);
                bookRepository.reduceStock(buyThisBook.getBookId(), newPurchase.getAmount());
                purchaseRepository.save(newPurchase);
                Link selfLink = linkTo(methodOn(PurchaseController.class).getPurchase(newPurchase.getPurchaseId())).withSelfRel();
                Link allPurchases = linkTo(methodOn(PurchaseController.class).getPurchases()).withRel("allPurchases");
                Link bookLink = linkTo(methodOn(BookController.class).getBook(buyThisBook.getBookId())).withRel("findBook");
                Link allBooks = linkTo(methodOn(BookController.class).getBooks()).withRel("allBooks");
                newPurchase.add(selfLink);
                newPurchase.add(allPurchases);
                newPurchase.add(bookLink);
                newPurchase.add(allBooks);
            } else {
                response = new ResponseEntity("{\"message\": \"You cannot buy more than there is stock\"}", headers, HttpStatus.CONFLICT);
            }
        }
        
        return response;
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> deleteBook(@PathVariable long bookId) {
        HttpHeaders headers = new HttpHeaders();
        Book deleteThisBook = bookRepository.findOne(bookId);
        ResponseEntity<Book> response = new ResponseEntity(HttpStatus.NOT_FOUND);
        
        if (deleteThisBook != null) {
            headers.setLocation(linkTo(BookController.class).slash(deleteThisBook.getBookId()).toUri());
            response = new ResponseEntity(deleteThisBook, headers, HttpStatus.OK);
            
            bookRepository.delete(deleteThisBook);
            Link allBooks = linkTo(methodOn(BookController.class).getBooks()).withRel("allBooks");
            deleteThisBook.add(allBooks);
        }
                
        return response;
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