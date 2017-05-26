package com.bookworm.controllers;

import com.bookworm.models.Genre;
import com.bookworm.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class that handles all book's genre API endpoints.
 * 
 * @version 2017.0522
 * @author Toni Seppalainen toni.seppalainen@cs.tamk.fi
 * @since 1.7
 */
@RestController
@Scope("singleton")
@RequestMapping(value = "/api/genres")
public class GenreController {

    /**
     * Book's genre repository.
     */
    @Autowired
    GenreRepository genreRepository;

    /**
     * Default constructor for Spring.
     */
    public GenreController() {}

    /**
     * Get all genres used in books.
     * 
     * @return array of genres as json.
     */
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Genre> getGenres() {
        return genreRepository.findAll();
    }
}
