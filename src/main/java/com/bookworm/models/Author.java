/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookworm.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.ArrayList;
import java.util.List;
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
public class Author extends ResourceSupport {
    
    private long authorId;
    private String firstName;
    private String lastName;
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
    @JsonBackReference(value = "authorToBookReference")
    public List<Book> getBookAuthor() {
        return new ArrayList<>(bookAuthor);

    }

    public void setBookAuthor(List<Book> books) {
        this.bookAuthor = new ArrayList<>(books);
    }
    
    public void addBookAuthor(Book book){
        if(! bookAuthor.contains(book)){
            bookAuthor.add(book);
            
            book.addAuthor(this);
        }
    }

    public void removeBookAuthor(Book book){
        if(bookAuthor.contains(book)){
            bookAuthor.remove(book);
            
            book.removeAuthor(this);
        }
    }
}
