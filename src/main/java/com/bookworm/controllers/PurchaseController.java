package com.bookworm.controllers;

import com.bookworm.models.*;
import com.bookworm.repositories.BookRepository;
import com.bookworm.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.http.HttpHeaders;
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
                Link bookLink = linkTo(methodOn(BookController.class).getBook(purchase.getBook().getBookId())).withRel("findBook");
                Link allBooks = linkTo(methodOn(BookController.class).getBooks()).withRel("allBooks");
                purchase.add(selfLink);
                purchase.add(bookLink);
                purchase.add(allBooks);
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
            Link bookLink = linkTo(methodOn(BookController.class).getBook(findThisPurchase.getBook().getBookId())).withRel("findBook");
            Link allPurchases = linkTo(methodOn(PurchaseController.class).getPurchases()).withRel("allPurchases");
            findThisPurchase.add(selfLink);
            findThisPurchase.add(bookLink);
            findThisPurchase.add(allPurchases);
        }
        
        return response;
    }
}