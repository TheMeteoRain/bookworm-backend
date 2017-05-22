package com.bookworm.controllers;

import com.bookworm.models.Genre;
import com.bookworm.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/genres")
public class GenreController {

    @Autowired
    GenreRepository database;

    public GenreController() {

    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Genre> getGenres() {
        return database.findAll();
    }

}
