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
@Table(name="publisher")
public class Publisher extends ResourceSupport {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long pubilsherId;
    private String name;

    public Publisher() {
        
    }

    public Publisher(String name) {
        this.name = name;
    }

    public long getPublisherId() {
        return pubilsherId;
    }

    public void setPublisherId(long PublisherId) {
        this.pubilsherId = PublisherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
