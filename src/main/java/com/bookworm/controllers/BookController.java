package com.bookworm.controllers;

import com.bookworm.models.*;
import com.bookworm.repositories.*;
import com.bookworm.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import validate.ValidationError;
import validate.ValidationErrorBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Class that handles all basic book API endpoints GET, POST and DELETE.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @since 1.7
 */
@RestController
@Scope("singleton")
@RequestMapping(value = "/api/books")
public class BookController {

    /**
     * Defines an object to provide client request information to a servlet.
     */
    @Resource
    private HttpServletRequest request;
    
    /**
     * Book repository.
     */
    @Autowired
    private BookRepository bookRepository;
    
    /**
     * Purchase repository.
     */
    @Autowired
    private PurchaseRepository purchaseRepository;
    
    /**
     * Member repository.
     */
    @Autowired
    private MemberRepository memberRepository;
    
    /**
     * Author repository.
     */
    @Autowired
    private AuthorRepository authorRepository;
    
    /**
     * Publisher repository.
     */
    @Autowired
    private PublisherRepository publisherRepository;
    
    /**
     * Notification repository.
     */
    @Autowired
    private NotificationRepository notificationRepository;
    
    /**
     * Simple email sender. It is assumed that your localhost is connected to 
     * the Internet and capable enough to send an e-mail.
     */
    @Autowired
    MailSender mailSender;
    
    /**
     * Default constructor for Spring.
     */
    public BookController() {}

    /**
     * Fetches all books from database and returns them.
     * 
     * @return array of books as json
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBooks(@RequestParam("page") int page) {

        Iterable<Book> books = bookRepository.findAll(new PageRequest(page - 1, 3));
        ResponseEntity<Book> response = new ResponseEntity(HttpStatus.NOT_FOUND);

        if (books != null) {
            response = new ResponseEntity(books, HttpStatus.OK);
            for (Book book : books) {
                Link selfLink = linkTo(BookController.class).slash(book.getBookId()).withSelfRel();
                Link buyLink = linkTo(methodOn(BookController.class).buyBook(book.getBookId(), 0)).withRel("buyBook");
                Link getReviewLink = linkTo(methodOn(ReviewController.class).getReviewsForBook(book.getBookId())).withRel("getReview");
                Link setReviewLink = linkTo(methodOn(ReviewController.class).saveReview(book.getBookId(), null)).withRel("setReview");
                Link getNotificationLink = linkTo(methodOn(NotificationController.class).getNotificationsByBook(book.getBookId())).withRel("getNotifications");
                Link setNotificationLink = linkTo(methodOn(NotificationController.class).setNotification(book.getBookId())).withRel("setNotification");
                book.add(selfLink);
                book.add(buyLink);
                book.add(getReviewLink);
                book.add(setReviewLink);
                book.add(getNotificationLink);
                book.add(setNotificationLink);
                Link addStockLink = linkTo(methodOn(BookController.class).addStockForBook(book.getBookId(), 0)).withRel("addStock");
                book.add(addStockLink);
            }
        }

        return response;
    }

    /**
     * Fetches one book by id from database and returns it.
     * 
     * @param bookId book's id in database
     * @return book as a json
     */
    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBook(@PathVariable long bookId) {
        Book findThisBook = bookRepository.findOne(bookId);
        ResponseEntity<Book> response = new ResponseEntity(HttpStatus.NOT_FOUND);

        if (findThisBook != null) {
            response = new ResponseEntity(findThisBook, HttpStatus.OK);

            Link selfLink = linkTo(BookController.class).slash(findThisBook.getBookId()).withSelfRel();
            Link buyLink = linkTo(methodOn(BookController.class).buyBook(findThisBook.getBookId(), 0)).withRel("buyBook");
            Link getReviewLink = linkTo(methodOn(ReviewController.class).getReviewsForBook(findThisBook.getBookId())).withRel("getReview");
            Link setReviewLink = linkTo(methodOn(ReviewController.class).saveReview(findThisBook.getBookId(), null)).withRel("setReview");
            Link getNotificationLink = linkTo(methodOn(NotificationController.class).getNotificationsByBook(findThisBook.getBookId())).withRel("getNotifications");
            Link setNotificationLink = linkTo(methodOn(NotificationController.class).setNotification(findThisBook.getBookId())).withRel("setNotification");
            findThisBook.add(selfLink);
            findThisBook.add(buyLink);
            findThisBook.add(getReviewLink);
            findThisBook.add(setReviewLink);
            findThisBook.add(getNotificationLink);
            findThisBook.add(setNotificationLink);
            
            Link addStockLink = linkTo(methodOn(BookController.class).addStockForBook(findThisBook.getBookId(), 0)).withRel("addStock");
            findThisBook.add(addStockLink);
        }

        return response;
    }
    
    /**
     * Used to save one book to the database.
     * 
     * Format:
     * {
     *   "authors": [
     *     {"authorId": 1},
     *     {"authorId": 2}
     *   ],
     *   "publisher": {
     *     "publisherId": 1
     *   },
     *   "title": "test",
     *   "description": "Selitysta",
     *   "genre": "Java",
     *   "format": "Kovakantinen",
     *   "price": 5,
     *   "stock": 15,
     *   "pages": 666,
     *   "isbn": "2340980293"
     * }
     * 
     * @param book
     * @return the saved book.
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> saveBook(@Validated @RequestBody Book book) {
        HttpHeaders headers = new HttpHeaders();
        AuthenticatedUser checkIfUserIsAdmin = AuthenticatedUser.fromRequest(request);
        
        if (checkIfUserIsAdmin.admin) {
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
            Link allBooks = linkTo(methodOn(BookController.class).getBooks(1)).withRel("allBooks");
            Link buyLink = linkTo(methodOn(BookController.class).buyBook(saveThisBook.getBookId(), 0)).withRel("buyBook");
            Link addStockLink = linkTo(methodOn(BookController.class).addStockForBook(saveThisBook.getBookId(), 0)).withRel("addStock");
            Link getReviewLink = linkTo(methodOn(ReviewController.class).getReviewsForBook(saveThisBook.getBookId())).withRel("getReview");
            Link setReviewLink = linkTo(methodOn(ReviewController.class).saveReview(saveThisBook.getBookId(), null)).withRel("setReview");
            Link getNotificationLink = linkTo(methodOn(NotificationController.class).getNotificationsByBook(saveThisBook.getBookId())).withRel("getNotifications");
            Link setNotificationLink = linkTo(methodOn(NotificationController.class).setNotification(saveThisBook.getBookId())).withRel("setNotification");
            saveThisBook.add(selfLink);
            saveThisBook.add(allBooks);
            saveThisBook.add(buyLink);
            saveThisBook.add(addStockLink);
            saveThisBook.add(getReviewLink);
            saveThisBook.add(setReviewLink);
            saveThisBook.add(getNotificationLink);
            saveThisBook.add(setNotificationLink);

            headers.setLocation(linkTo(BookController.class).slash(saveThisBook.getBookId()).toUri());
        
            return new ResponseEntity(saveThisBook, headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity("{\"code\": 401, \"status\": \"Unauthorized\", \"message\": \"You do not have permission\"}", headers, HttpStatus.UNAUTHORIZED);   
        }
    }
    
    /**
     * Buy the book with the given id.
     * 
     * Reduces book's stock, adds history about the payment to the purhcase
     * history.
     * 
     * @param bookId book's id.
     * @param amount the amount of books bought.
     * @return purhcase history.
     */
    @Transactional
    @RequestMapping(value="/{bookId}/buy", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
                buyThisBook.setStock(buyThisBook.getStock() - amount);
                bookRepository.save(buyThisBook);
                purchaseRepository.save(newPurchase);
                Link selfLink = linkTo(methodOn(PurchaseController.class).getPurchase(newPurchase.getPurchaseId())).withSelfRel();
                newPurchase.add(selfLink);

                Link selfBookLink = linkTo(BookController.class).slash(newPurchase.getBook().getBookId()).withSelfRel();
                Link allBooks = linkTo(methodOn(BookController.class).getBooks(1)).withRel("allBooks");
                Link buyLink = linkTo(methodOn(BookController.class).buyBook(newPurchase.getBook().getBookId(), 0)).withRel("buyBook");
                Link addStockLink = linkTo(methodOn(BookController.class).addStockForBook(newPurchase.getBook().getBookId(), 0)).withRel("addStock");
                Link getReviewLink = linkTo(methodOn(ReviewController.class).getReviewsForBook(newPurchase.getBook().getBookId())).withRel("getReview");
                Link setReviewLink = linkTo(methodOn(ReviewController.class).saveReview(newPurchase.getBook().getBookId(), null)).withRel("setReview");
                Link getNotificationLink = linkTo(methodOn(NotificationController.class).getNotificationsByBook(newPurchase.getBook().getBookId())).withRel("getNotifications");
                Link setNotificationLink = linkTo(methodOn(NotificationController.class).setNotification(newPurchase.getBook().getBookId())).withRel("setNotification");
                newPurchase.getBook().add(selfBookLink);
                newPurchase.getBook().add(allBooks);
                newPurchase.getBook().add(buyLink);
                newPurchase.getBook().add(addStockLink);
                newPurchase.getBook().add(getReviewLink);
                newPurchase.getBook().add(setReviewLink);
                newPurchase.getBook().add(getNotificationLink);
                newPurchase.getBook().add(setNotificationLink);

            } else {
                response = new ResponseEntity("{\"code\": 409, \"status\": \"Conflict\", \"message\": \"You cannot buy more than there is stock\"}", headers, HttpStatus.CONFLICT);
            }
        }

        return response;
    }
  
    /**
     * Adds stock to the given book. Also checks for any notifications left
     * by users and sends email for those who have left notification.
     * 
     * @param bookId books'id id.
     * @param amount the amount of increased stock.
     * @return the targeted book.
     */
    @Transactional
    @RequestMapping(value="/{bookId}/add_stock", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> addStockForBook(@PathVariable long bookId, @RequestBody int amount) {
        HttpHeaders headers = new HttpHeaders();
      
        ResponseEntity<Book> response;
        AuthenticatedUser checkIfUserIsAdmin = AuthenticatedUser.fromRequest(request);
        
        if (checkIfUserIsAdmin.admin) {
            Member findThisMember = memberRepository.findOne(checkIfUserIsAdmin.id);
            Book findThisBook = bookRepository.findOne(bookId);
       
            if (findThisBook != null) {
                headers.setLocation(linkTo(BookController.class).slash(findThisBook.getBookId()).toUri());

                if (findThisBook.getStock() == 0) {
                    sendMail(findThisBook);
                }
                findThisBook.setStock(findThisBook.getStock() + amount);
                bookRepository.save(findThisBook);

                response = new ResponseEntity(findThisBook, headers, HttpStatus.OK);

                Link selfLink = linkTo(BookController.class).slash(findThisBook.getBookId()).withSelfRel();
                Link buyLink = linkTo(methodOn(BookController.class).buyBook(findThisBook.getBookId(), 0)).withRel("buyBook");
                Link addStockLink = linkTo(methodOn(BookController.class).addStockForBook(findThisBook.getBookId(), 0)).withRel("addStock");
                Link getReviewLink = linkTo(methodOn(ReviewController.class).getReviewsForBook(findThisBook.getBookId())).withRel("getReview");
                Link setReviewLink = linkTo(methodOn(ReviewController.class).saveReview(findThisBook.getBookId(), null)).withRel("setReview");
                Link getNotificationLink = linkTo(methodOn(NotificationController.class).getNotificationsByBook(findThisBook.getBookId())).withRel("getNotifications");
                Link setNotificationLink = linkTo(methodOn(NotificationController.class).setNotification(findThisBook.getBookId())).withRel("setNotification");
                findThisBook.add(selfLink);
                findThisBook.add(buyLink);
                findThisBook.add(addStockLink);
                findThisBook.add(getReviewLink);
                findThisBook.add(setReviewLink);
                findThisBook.add(getNotificationLink);
                findThisBook.add(setNotificationLink);
            } else {
                response = new ResponseEntity("{\"code\": 404, \"status\": \"Not Found\", \"message\": \"Book not found\"}", headers, HttpStatus.NOT_FOUND);
            }
        } else {
            response = new ResponseEntity("{\"code\": 401, \"status\": \"Unauthorized\", \"message\": \"You do not have permission\"}", headers, HttpStatus.UNAUTHORIZED);
        }

        return response;
    }

    /**
     * Delete given book.
     * 
     * @param bookId book's id.
     * @return deleted book.
     */
    @RequestMapping(value = "/{bookId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> deleteBook(@PathVariable long bookId) {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Book> response = new ResponseEntity(HttpStatus.NOT_FOUND);
        AuthenticatedUser checkIfUserIsAdmin = AuthenticatedUser.fromRequest(request);
        if (checkIfUserIsAdmin.admin) {
            Book deleteThisBook = bookRepository.findOne(bookId);
        
            if (deleteThisBook != null) {
                headers.setLocation(linkTo(BookController.class).slash(deleteThisBook.getBookId()).toUri());
                response = new ResponseEntity(deleteThisBook, headers, HttpStatus.OK);

                bookRepository.delete(deleteThisBook);
                Link allBooks = linkTo(methodOn(BookController.class).getBooks(1)).withRel("allBooks");
                deleteThisBook.add(allBooks);
            }
        } else {
            response = new ResponseEntity("{\"code\": 401, \"status\": \"Unauthorized\", \"message\": \"You do not have permission\"}", headers, HttpStatus.UNAUTHORIZED);
        }
        
                
        return response;
    }

    /**
     * Returns books with given word in it's data.
     *
     * @param searchWord Word to look for
     * @param pageNum page of items
     * @return books that match
     */
    @RequestMapping(value="/search", method=RequestMethod.GET)
    public Iterable<Book> searchBooks(@RequestParam("search") String searchWord, @RequestParam("page") int pageNum) {
        return bookRepository.findDistinctByTitleContainingOrDescriptionContainingOrAuthors_FirstNameContainingOrAuthors_LastNameContainingOrGenreContainingAllIgnoreCase(searchWord, searchWord, searchWord, searchWord, searchWord, new PageRequest(pageNum - 1, 3));
    }

    /**
     * Returns books with given genre
     *
     * @param genre genre to look for
     * @param pageNum page of items
     * @return Books that match
     */
    @RequestMapping(value="/genre", method=RequestMethod.GET)
    public Iterable<Book> searchByGenre(@RequestParam("genre") String genre, @RequestParam("page") int pageNum) {
        return bookRepository.findByGenre(genre, new PageRequest(pageNum - 1, 3));
    }

    /**
     * Exception handler for mistyped book.
     * 
     * @param exception 
     * @return information about the error.
     */

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationError handleException(MethodArgumentNotValidException exception) {
        return createValidationError(exception);
    }

    /**
     * Validates book input.
     * 
     * @param error
     * @return list of errors.
     */
    private ValidationError createValidationError(MethodArgumentNotValidException error) {
        return ValidationErrorBuilder.fromBindingErrors(error.getBindingResult());
    }

    /**
     * Goes through all notifications for the given book and send email.
     * 
     * @param book 
     */
    private void sendMail(Book book) {
        List<Notification> notifications = notificationRepository.findByBook(book);

        for (Notification notification : notifications) {
            String email = notification.getMember().getEmail();
            String bookName = notification.getBook().getTitle();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Bookworm - book notification");
            message.setText(bookName + " is now in stock");
            message.setTo(email);
            message.setFrom("bookworm.mail.service@gmail.com");
            try {
                mailSender.send(message);
                System.out.println("{\"message\": \"OK\"}");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("{\"message\": \"Error\"}");
            }

            notificationRepository.delete(notification);
        }
    }
}
