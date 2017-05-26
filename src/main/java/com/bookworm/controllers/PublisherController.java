package com.bookworm.controllers;

import com.bookworm.models.Publisher;
import com.bookworm.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class that handles all book's publisher API endpoints.
 *
 * @author Toni Seppalainen toni.seppalainen@cs.tamk.fi
 * @version 2017.0522
 * @since 1.7
 */
@RestController
@Scope("singleton")
@RequestMapping(value = "/api/publishers")
public class PublisherController {

    /**
     * Publisher repository.
     */
    @Autowired
    private PublisherRepository publisherRepository;

    /**
     * Default constructor for Spring.
     */
    public PublisherController() {
    }

    /**
     * Fetches all publishers or the ones matching the search term.
     *
     * @param term find publisher name by this text.
     * @return array of publishers as json.
     */
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Publisher> getPublishers(@RequestParam("search") String term) {
        if (term != null) {
            return publisherRepository.findByNameContainingIgnoreCase(term);
        } else {
            return publisherRepository.findAll();
        }
    }

}
