package com.bookworm.controllers;

import com.bookworm.models.Author;
import com.bookworm.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/authors")
public class AuthorController {

    @Autowired
    AuthorRepository database;

    public AuthorController() {

    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Author> getAuthors(@RequestParam("search") String term) {
        if (term != null) {
            return database.findByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(term, term);
        } else {
            return database.findAll();
        }
    }

}
