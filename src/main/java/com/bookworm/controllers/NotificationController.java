package com.bookworm.controllers;

import com.bookworm.models.Book;
import com.bookworm.models.Member;
import com.bookworm.models.Notification;
import com.bookworm.models.Review;
import com.bookworm.models.ReviewData;
import com.bookworm.repositories.AuthorRepository;
import com.bookworm.repositories.BookRepository;
import com.bookworm.repositories.MemberRepository;
import com.bookworm.repositories.NotificationRepository;
import com.bookworm.repositories.PublisherRepository;
import com.bookworm.repositories.PurchaseRepository;
import com.bookworm.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.hateoas.Link;
import java.util.Set;
import javax.annotation.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping(value = "books/{bookId}/notifications")
public class NotificationController {

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
    
    @Autowired
    NotificationRepository notificationRepository;

    public NotificationController() {
        
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> getNotificationsByBook(@PathVariable long bookId) {
        Set<Notification> notifications = bookRepository.findOne(bookId).getNotifications();
        ResponseEntity<Notification> response = new ResponseEntity(HttpStatus.NOT_FOUND);
        
        if (notifications != null) {
            response = new ResponseEntity(notifications, HttpStatus.OK);
            
            for (Notification notification : notifications) {
                
                Link notificationsByBook = linkTo(methodOn(NotificationController.class).getNotificationsByBook(notification.getBook().getBookId())).withRel("findNotificationsByBook");
                notification.add(notificationsByBook);
            }
        }
        
        return response;
    }
    
    @Transactional
    @RequestMapping(method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> setNotification(@PathVariable long bookId) {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Notification> response = new ResponseEntity(HttpStatus.NOT_FOUND);
        Member findThisMember = memberRepository.findOne(AuthenticatedUser.fromRequest(request).id);
        Book findThisBook = bookRepository.findOne(bookId);
        
        if (findThisBook != null && findThisBook.getStock() == 0) {
            Notification saveThisNotification = new Notification(findThisBook, findThisMember);
            headers.setLocation(linkTo(PurchaseController.class).slash(saveThisNotification.getId()).toUri());
            
            boolean added = findThisBook.getNotifications().add(saveThisNotification);
            if (added) {
               // notificationRepository.save(saveThisNotification);
               // memberRepository.save(findThisMember);
               // bookRepository.save(findThisBook);
                
                Link notificationsByBook = linkTo(methodOn(NotificationController.class).getNotificationsByBook(saveThisNotification.getBook().getBookId())).withRel("findNotificationsByBook");
                saveThisNotification.add(notificationsByBook);
                
                response = new ResponseEntity(saveThisNotification, headers, HttpStatus.CREATED);
            } else {
                // Member can't post multiple reviews for a single book
                response = new ResponseEntity("{\"code\": 409, \"status\": \"Conflict\", \"message\": \"Member can only once subscribe for a notification per book\"}", headers, HttpStatus.CONFLICT);
            }
        } else {
            response = new ResponseEntity("{\"code\": 409, \"status\": \"Conflict\", \"message\": \"Book stock must be 0\"}", headers, HttpStatus.CONFLICT);
        }
        
        return response;
    }
}