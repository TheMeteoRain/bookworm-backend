package com.bookworm.controllers;

import com.bookworm.models.*;
import com.bookworm.repositories.BookRepository;
import com.bookworm.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Scope("singleton")
@RestController
@RequestMapping(value = "/purchases")
public class PurchaseController {

    @Autowired
    BookRepository bookRepository;
    
    @Autowired
    PurchaseRepository purchaseRepository;
    
    public PurchaseController() {
        
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Purchase> getPurchases() {
        Iterable<Purchase> purchases = purchaseRepository.findAll();
        ResponseEntity<Purchase> response = new ResponseEntity(HttpStatus.NOT_FOUND);
        
        if (purchases != null) {
            response = new ResponseEntity(purchases, HttpStatus.OK);
            
            for (Purchase purchase : purchases) {
                Link selfLink = linkTo(methodOn(PurchaseController.class).getPurchase(purchase.getPurchaseId())).withSelfRel();
                purchase.add(selfLink);
                
                Link selfBookLink = linkTo(BookController.class).slash(purchase.getBook().getBookId()).withSelfRel();
                Link allBooks = linkTo(methodOn(BookController.class).getBooks(1)).withRel("allBooks");
                Link buyLink = linkTo(methodOn(BookController.class).buyBook(purchase.getBook().getBookId(), 0)).withRel("buyBook");
                Link addStockLink = linkTo(methodOn(BookController.class).addStockForBook(purchase.getBook().getBookId(), 0)).withRel("addStock");
                Link getReviewLink = linkTo(methodOn(ReviewController.class).getReviewsForBook(purchase.getBook().getBookId())).withRel("getReview");
                Link setReviewLink = linkTo(methodOn(ReviewController.class).saveReview(purchase.getBook().getBookId(), null)).withRel("setReview");
                Link getNotificationLink = linkTo(methodOn(NotificationController.class).getNotificationsByBook(purchase.getBook().getBookId())).withRel("getNotifications");
                Link setNotificationLink = linkTo(methodOn(NotificationController.class).setNotification(purchase.getBook().getBookId())).withRel("setNotification");
                purchase.getBook().add(selfBookLink);
                purchase.getBook().add(allBooks);
                purchase.getBook().add(buyLink);
                purchase.getBook().add(addStockLink);
                purchase.getBook().add(getReviewLink);
                purchase.getBook().add(setReviewLink);
                purchase.getBook().add(getNotificationLink);
                purchase.getBook().add(setNotificationLink);
            }
        }
        
        return response;
    }
    
    @RequestMapping(value = "/{purchaseId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Purchase> getPurchase(@PathVariable long purchaseId) {
        Purchase findThisPurchase = purchaseRepository.findOne(purchaseId);
        ResponseEntity<Purchase> response = new ResponseEntity(HttpStatus.NOT_FOUND);
        
        if (findThisPurchase != null) {
            response = new ResponseEntity(findThisPurchase, HttpStatus.OK);
            
            Link selfLink = linkTo(methodOn(PurchaseController.class).getPurchase(findThisPurchase.getPurchaseId())).withSelfRel();
            findThisPurchase.add(selfLink);

            Link selfBookLink = linkTo(BookController.class).slash(findThisPurchase.getBook().getBookId()).withSelfRel();
            Link allBooks = linkTo(methodOn(BookController.class).getBooks(1)).withRel("allBooks");
            Link buyLink = linkTo(methodOn(BookController.class).buyBook(findThisPurchase.getBook().getBookId(), 0)).withRel("buyBook");
            Link addStockLink = linkTo(methodOn(BookController.class).addStockForBook(findThisPurchase.getBook().getBookId(), 0)).withRel("addStock");
            Link getReviewLink = linkTo(methodOn(ReviewController.class).getReviewsForBook(findThisPurchase.getBook().getBookId())).withRel("getReview");
            Link setReviewLink = linkTo(methodOn(ReviewController.class).saveReview(findThisPurchase.getBook().getBookId(), null)).withRel("setReview");
            Link getNotificationLink = linkTo(methodOn(NotificationController.class).getNotificationsByBook(findThisPurchase.getBook().getBookId())).withRel("getNotifications");
            Link setNotificationLink = linkTo(methodOn(NotificationController.class).setNotification(findThisPurchase.getBook().getBookId())).withRel("setNotification");
            findThisPurchase.getBook().add(selfBookLink);
            findThisPurchase.getBook().add(allBooks);
            findThisPurchase.getBook().add(buyLink);
            findThisPurchase.getBook().add(addStockLink);
            findThisPurchase.getBook().add(getReviewLink);
            findThisPurchase.getBook().add(setReviewLink);
            findThisPurchase.getBook().add(getNotificationLink);
            findThisPurchase.getBook().add(setNotificationLink);
        }
        
        return response;
    }
}