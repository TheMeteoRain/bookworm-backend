package com.bookworm.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by tonis on 2017-05-22.
 */
@Entity
@Table(name = "genre")
public class Genre {

    private String name;

    public Genre() {
    }

    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
