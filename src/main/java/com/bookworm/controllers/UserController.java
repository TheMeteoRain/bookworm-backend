package com.bookworm.controllers;

import com.bookworm.models.User;
import com.bookworm.repositories.UserRepository;
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
public class UserController {

    @Autowired
    UserRepository database;
    
    public UserController() {
        
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public void savePoint(@RequestBody User c) {
        database.save(c);
    }

    @RequestMapping(method=RequestMethod.GET)
    public Iterable<User> getLocations() {
        return database.findAll();
    }
    
    @RequestMapping(value="/{locationId}", method=RequestMethod.GET)
    public User fetchLocation(@PathVariable long locationId) {
        return database.findOne(locationId);
    }
    
    @RequestMapping("/example")
    public User greeting() {
        return new User();
    }
}