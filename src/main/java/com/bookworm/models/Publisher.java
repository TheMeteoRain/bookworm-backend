/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookworm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Akash
 */
@Entity
@Table(name="publisher")
public class Publisher extends ResourceSupport {
    
    private long publisherId;
    private String name;
    private String country;
    private String city;
    private List<Book> books;

    public Publisher() {
        
    }

    public Publisher(String name, String country, String city) {
        this.name = name;
        this.country = country;
        this.city = city;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(long PublisherId) {
        this.publisherId = PublisherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    public List<Book> getBooks() {
        return books;
    }
    
    @JsonProperty
    public void setBooks(List<Book> books) {
        this.books = books;
    }
    
    
}
