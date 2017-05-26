package com.bookworm.controllers;

import com.bookworm.models.Author;
import com.bookworm.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides search access to authors in database.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0522
 * @since 1.7
 */
@RestController
@RequestMapping(value = "/api/authors")
public class AuthorController {

    /**
     * Handle to author repository.
     */
    @Autowired
    AuthorRepository database;

    /**
     * Empty constructor for Spring.
     */
    public AuthorController() {

    }

    /**
     * Retrieves all authors or found matches for the given search term.
     *
     * @param term The input to search with.
     * @return All found authors.
     */
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Author> getAuthors(@RequestParam("search") String term) {
        if (term != null) {
            return database.findByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(term, term);
        } else {
            return database.findAll();
        }
    }

}
