/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookworm.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Akash
 */
@Entity
@Table(name="author")
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property = "authorId", scope = Author.class)
public class Author extends ResourceSupport implements Serializable {
    
    private long authorId;
    private String firstName;
    private String lastName;
    @JsonBackReference
    //@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property = "bookId")
    private List<Book> bookAuthor = new ArrayList<>();
    
    public Author() {
        
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ManyToMany(mappedBy = "authors")
    public List<Book> getBookAuthor() {
        return new ArrayList<>(bookAuthor);

    }

    public void setBookAuthor(List<Book> books) {
        this.bookAuthor = new ArrayList<>(books);
    }
    
    public void addBookAuthor(Book book){

        //avoid circular calls : assumes equals and hashcode implemented
        if(! bookAuthor.contains(book)){
            bookAuthor.add(book);
            
            book.addAuthor(this);
        }
    }

    public void removeBookAuthor(Book book){

        //avoid circular calls: assumes equals and hashcode implemented: 
        if(bookAuthor.contains(book)){
            bookAuthor.remove(book);
            
            book.removeAuthor(this);
        }
    }
}
