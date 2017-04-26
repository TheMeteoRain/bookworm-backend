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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Purchase> getPurchases() {
        Iterable<Purchase> purchases = purchaseRepository.findAll();
        for (Purchase purchase : purchases) {
            Link selfLink = linkTo(methodOn(PurchaseController.class).getPurchase(purchase.getPurchaseId())).withSelfRel();
            Link bookLink = linkTo(methodOn(BookController.class).getBook(purchase.getBookId())).withRel("findBook");
            purchase.add(selfLink);
            purchase.add(bookLink);
        }
        
        return new ResponseEntity(purchases, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{purchaseId}", method = RequestMethod.GET)
    public ResponseEntity<Purchase> getPurchase(@PathVariable long purchaseId) {
        Purchase purchase = purchaseRepository.findOne(purchaseId);
        Link selfLink = linkTo(methodOn(PurchaseController.class).getPurchase(purchase.getPurchaseId())).withSelfRel();
        Link bookLink = linkTo(methodOn(BookController.class).getBook(purchase.getBookId())).withRel("findBook");
        purchase.add(selfLink);
        purchase.add(bookLink);
        return new ResponseEntity(purchase, HttpStatus.OK);
    }
}