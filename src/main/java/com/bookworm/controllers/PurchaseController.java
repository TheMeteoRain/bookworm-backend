package com.bookworm.controllers;

import com.bookworm.models.*;
import com.bookworm.repositories.BookRepository;
import com.bookworm.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        HttpHeaders headers = new HttpHeaders();
        Iterable<Purchase> purchases = purchaseRepository.findAll();
        for (Purchase purchase : purchases) {
            
        }
        
        headers.setLocation(linkTo(PurchaseController.class).toUri());
        
        return new ResponseEntity(purchases, headers, HttpStatus.OK);
    }
}