/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookworm.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Akash
 */
@Entity
@Table(name="author")
public class Author extends ResourceSupport {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long authorId;
    private String first_name;
    private String last_name;
    
    public Author() {
        
    }

    public Author(long authorId, String first_name, String last_name) {
        this.authorId = authorId;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    
    
}
