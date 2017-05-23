package com.bookworm.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class representation of book's genre.
 * 
 * @version 2017.0522
 * @author Toni Seppalainen toni.seppalainen@cs.tamk.fi
 * @since 1.7
 */
@Entity
@Table(name = "genre")
public class Genre {

    /**
     * Genre's name.
     */
    private String name;

    /**
     * Default constructor for Spring.
     */
    public Genre() {}

    /**
     * Gets the name of this genre.
     * 
     * @return name of this genre.
     */
    @Id
    public String getName() {
        return name;
    }

    /**
     * Changes the name of this genre.
     * 
     * @param name this genre's new name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
