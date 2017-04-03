package com.bookworm.models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
 
@Entity
public class Book extends Item {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long authorId;
    private long publisherId;
    private String description;
    private String genre;
    private String format;
    private String language;
    private int pages;
    private String isbn;

    public Book(long id, long authorId, long publisherId, String title, String description, long stock, double rating, String genre, String format, String language, int pages, String isbn) {
        super(id, title, stock, rating);
        this.authorId = authorId;
        this.publisherId = publisherId;
        this.description = description;
        this.genre = genre;
        this.format = format;
        this.language = language;
        this.pages = pages;
        this.isbn = isbn;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(long publisherId) {
        this.publisherId = publisherId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    
}
