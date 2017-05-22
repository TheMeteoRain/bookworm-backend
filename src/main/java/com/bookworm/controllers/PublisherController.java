package com.bookworm.controllers;

import com.bookworm.models.Publisher;
import com.bookworm.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/publishers")
public class PublisherController {

    @Autowired
    PublisherRepository database;

    public PublisherController() {

    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Publisher> getGenres(@RequestParam("search") String term) {
        if (term != null) {
            return database.findByNameContaining(term);
        } else {
            return database.findAll();
        }
    }

}
