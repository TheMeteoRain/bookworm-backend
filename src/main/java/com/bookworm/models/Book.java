package com.bookworm.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name="book")
public class Book {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private long authorId;
    private long publisherId;
    private String title;
    private String description;
    private String genre;
    private String format;
    private String lang;
    private int pages;
    private long stock;
    private double rating;
    private String isbn;
    
    public Book() {
        
    }

    public Book(long id, long authorId, long publisherId, String title, String description, String genre, String format, String language, int pages, long stock, double rating, String isbn) {
        this.id = id;
        this.authorId = authorId;
        this.publisherId = publisherId;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.format = format;
        this.lang = language;
        this.pages = pages;
        this.stock = stock;
        this.rating = rating;
        this.isbn = isbn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    
}
