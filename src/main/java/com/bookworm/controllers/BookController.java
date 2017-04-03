package com.bookworm.controllers;

import com.bookworm.models.*;
import com.bookworm.repositories.BookRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/books")
public class BookController {
    
    private List<Publisher> listOfPublishers = new ArrayList<>();
    private List<Author> listOfAuthors = new ArrayList<>();
    private List<Book> listOfBooks = new ArrayList<>();
    
    
    @Autowired
    BookRepository database;
    
    public BookController() {
        listOfPublishers.add(new Publisher(0, "Teos"));
        listOfPublishers.add(new Publisher(0, "Kustantaja"));
        listOfAuthors.add(new Author(0, "Arthur", "Conan Doyle"));
        listOfAuthors.add(new Author(0, "Anne", "Frank"));
        listOfBooks.add(new Book(
                0, listOfAuthors.get(0).getId(), listOfPublishers.get(0).getId(),
                "Sherlock Holmes", "Sherlock Holmes on maailman tunnetuin ja luultavasti nerokkain salapoliisi.",
                10, 5,"Viihde", "Kovakantinen", "Suomi", 1305, "9789518512465")
        );
        listOfBooks.add(new Book(
                0, listOfAuthors.get(1).getId(), listOfPublishers.get(1).getId(), 
                "Päiväkirja", "Anne Frankin päiväkirja tunnettiin aiemmin hänen isänsä Otto Frankin toimittamana Nuoren tytön päiväkirjana.",
                15, 3, "Viihde", "Nidottu, pehmeäkantinen", "Suomi", 421, "9789513143510")
        );
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public void savePoint(@RequestBody Book c) {
        database.save(c);
    }

    @RequestMapping(method=RequestMethod.GET)
    public Iterable<Book> getLocations() {
        return database.findAll();
    }
    
    @RequestMapping(value="/{locationId}", method=RequestMethod.GET)
    public Book fetchLocation(@PathVariable long locationId) {
        return database.findOne(locationId);
    }
    
    @RequestMapping("/example")
    public Book greeting() {
        return listOfBooks.get(0);
    }
}