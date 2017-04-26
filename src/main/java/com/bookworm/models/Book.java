package com.bookworm.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.hateoas.ResourceSupport;
 
@Entity
@Table(name="book")
public class Book extends ResourceSupport {
    
    private long bookId;
    
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
    @JsonIdentityReference(alwaysAsId = true)
    private Publisher publisher;

    @NotNull(message = "Title may not be empty")
    @Length(min = 1, max = 255, message = "Title length must be between 1 and 255")
    private String title;
    
    @NotBlank(message = "Description may not be empty")
    private String description;
    
    @Pattern(regexp="JS|Java|CSS|C|Python|JavaScript", message = "Genre must match JS, Java, CSS, C, Pyhton or JavaScript")
    @NotBlank(message = "Genre may not be empty")
    private String genre;
    
    @NotBlank(message = "Format may not be empty")
    private String format;
    
    @Min(value = 5, message = "Price must be greater than or equal to 5")
    private double price;
    
    private int stock;
    
    @Min(value = 1, message = "Pages must be greater than or equal to 1")
    private int pages;
    
    @NotBlank(message = "ISBN may not be empty")
    @Length(min = 10, max = 15, message = "ISBN length must be between 10 and 15")
    private String isbn;
    
    public Book() {
        
    }

    public Book(Publisher publisher, String title, String description, String genre, String format, double price, int stock, int pages, String isbn) {
        this.publisher = publisher;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.format = format;
        this.price = price;
        this.stock = stock;
        this.pages = pages;
        this.isbn = isbn;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    @ManyToOne
    @JoinColumn(name = "publisherId", referencedColumnName = "publisherId")
    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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
