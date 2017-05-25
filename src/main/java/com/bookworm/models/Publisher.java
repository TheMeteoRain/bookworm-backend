package com.bookworm.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.hateoas.ResourceSupport;

/**
 * Class that represents book's publisher.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @since 1.7
 */
@Entity
@Table(name="publisher")
public class Publisher extends ResourceSupport {
    
    /**
     * Publisher's database id.
     */
    private long publisherId;
    
    /**
     * Publisher's name.
     */
    private String name;
    
    /**
     * Publisher's country.
     */
    private String country;
    
    /**
     * Publisher's city.
     */
    private String city;
    
    /**
     * List of books that this publisher has published.
     */
    private List<Book> books;

    /**
     * Default constructor for Spring.
     */
    public Publisher() {}

    /**
     * Creates a new publisher with the given name, country and city.
     * 
     * @param name
     * @param country
     * @param city 
     */
    public Publisher(String name, String country, String city) {
        this.name = name;
        this.country = country;
        this.city = city;
    }

    /**
     * Gets the database id of this publisher.
     * 
     * @return this publisher's id.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long getPublisherId() {
        return publisherId;
    }

    /**
     * Changes the id of this publisher.
     * 
     * @param PublisherId this publisher's new id.
     */
    public void setPublisherId(long PublisherId) {
        this.publisherId = PublisherId;
    }

    /**
     * Gets the name of this publisher.
     * 
     * @return this publisher's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the name of this publisher.
     * 
     * @param name this publisher's new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the country of this publisher.
     * 
     * @return this publisher's country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Changes the country of this publisher.
     * 
     * @param country this publisher's new country.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the city of this publisher.
     * 
     * @return this publisher's city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Changes the city of this publisher.
     * 
     * @param city this publisher's new city.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Changes the book list of this publisher.
     * 
     * @return this publisher's new book list.
     */
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference(value = "publisherToBookReference")
    public List<Book> getBooks() {
        return books;
    }
    
    /**
     * Add a book to this publisher's book list.
     * 
     * @param books book to be added to the list.
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
