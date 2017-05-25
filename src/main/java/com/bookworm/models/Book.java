package com.bookworm.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.hateoas.ResourceSupport;
 
/**
 * Class representation of book.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @since 1.7
 */
@Entity
@Table(name="book")
public class Book extends ResourceSupport {
    
    /**
     * Book's database id.
     */
    private long bookId;
    
    /**
     * List of book's authors.
     */
    private List<Author> authors = new ArrayList<>();

    /**
     * Book's publisher, only one allowed.
     */
    private Publisher publisher;

    /**
     * Book's title.
     */
    @NotNull(message = "Title may not be empty")
    @Length(min = 1, max = 255, message = "Title length must be between 1 and 255")
    private String title;
    
    /**
     * Books description.
     */
    @NotBlank(message = "Description may not be empty")
    private String description;
    
    /**
     * Books genre, only one allowed.
     */
    @Pattern(regexp="JS|Java|CSS|C|Python|JavaScript", message = "Genre must match JS, Java, CSS, C, Pyhton or JavaScript")
    @NotBlank(message = "Genre may not be empty")
    private String genre;
    
    /**
     * 
     */
    @NotBlank(message = "Format may not be empty")
    private String format;
    
    /**
     * Book's price.
     */
    @Min(value = 5, message = "Price must be greater than or equal to 5")
    private double price;
    
    /**
     * Book's number of stock.
     */
    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    private int stock = 0;
    
    /**
     * Book's number of pages.
     */
    @Min(value = 1, message = "Pages must be greater than or equal to 1")
    private int pages;
    
    /**
     * Book's identifying isbn.
     */
    @NotBlank(message = "ISBN may not be empty")
    @Length(min = 10, max = 15, message = "ISBN length must be between 10 and 15")
    private String isbn;
  
    /**
     * This book's reviews.
     */
    private Set<Review> reviews = new HashSet<>();
    
    /**
     * This book's notifications.
     */
    private Set<Notification> notifications = new HashSet<>();

    /**
     * Default constructor for Spring.
     */
    public Book() {}

    /**
     * Creates a new book with list of authors, publisher, title, description,
     * genre, format, price, stock, pages and isbn.
     * 
     * @param authors list of authors.
     * @param publisher book's publisher.
     * @param title book's title.
     * @param description book's description.
     * @param genre book's genre.
     * @param format book's format.
     * @param price book's price.
     * @param stock book's stock.
     * @param pages page numbers.
     * @param isbn identifying isbn number.
     */
    public Book(List<Author> authors, Publisher publisher, String title, 
            String description, String genre, String format, 
            double price, int stock, int pages, String isbn) {
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
 
    /**
     * Gets the database id of this book.
     * 
     * @return this author's id.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long getBookId() {
        return bookId;
    }

    /**
     * Changes the id of this book.
     * 
     * @param bookId this book's new id.
     */
    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    /**
     * Get the list of authors this book haves.
     * 
     * @return this book's authors.
     */
    @ManyToMany
    @JoinTable(name = "bookAuthor", joinColumns = @JoinColumn(name = "bookId", referencedColumnName = "bookId"), inverseJoinColumns = @JoinColumn(name = "authorId", referencedColumnName = "authorId"))
    //@JsonManagedReference(value = "authorToBookReference")
    public List<Author> getAuthors() {
        return new ArrayList<>(authors);
    }

    /**
     * Changes the author list of this book.
     * 
     * @param authors this book's new author list.
     */
    public void setAuthors(List<Author> authors) {
        this.authors = new ArrayList<>(authors);
    }
    
    /**
     * Add an author to this book's author list.
     * 
     * @param author author to be added to the list.
     */
    public void addAuthor(Author author){
        if(! authors.contains(author)){
            authors.add(author);

            author.addBookAuthor(this);
        }
    }

    /**
     * Removes an author from this books's author list.
     * 
     * @param author 
     */
    public void removeAuthor(Author author){
        if(! authors.contains(author)){
            authors.remove(author);

            author.removeBookAuthor(this);
        }
    }

    /**
     * Gets the publisher of this book.
     * 
     * @return this book's publisher.
     */
    @ManyToOne
    @JoinColumn(name = "publisherId", referencedColumnName = "publisherId")
    //@JsonManagedReference(value = "publisherToBookReference")
    public Publisher getPublisher() {
        return publisher;
    }

    /**
     * Changes the publisher of this book.
     * 
     * @param publisher this book's new publisher.
     */
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Gets the title of this book.
     * 
     * @return this book's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Changes the title of this book.
     * 
     * @param title this book's new title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    
    /**
     * Gets the description of this book.
     * 
     * @return this book's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Changes the description of this book.
     * 
     * @param description  this book's new description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the genre of this book.
     * 
     * @return this book's genre.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Changes the genre of this book.
     * 
     * @param genre  this book's new genre.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Gets the format of this book.
     * 
     * @return this book's format.
     */
    public String getFormat() {
        return format;
    }

    /**
     * Changes the format of this book.
     * 
     * @param format this book's new format.
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Gets the price of this book.
     * 
     * @return this book's price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Changes the price of this book.
     * 
     * @param price this book's new price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the stock amount for this book.
     * 
     * @return this book's number of stocks.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Changes the stock number of this book.
     * 
     * @param stock this book's new stock number.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets number of pages this book haves.
     * 
     * @return this book's number of pages.
     */
    public int getPages() {
        return pages;
    }

    /**
     * Changes the page number of this book.
     * 
     * @param pages this book's new page number.
     */
    public void setPages(int pages) {
        this.pages = pages;
    }

    /**
     * Gets the isbn of this book.
     * 
     * @return this book's isbn.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Changes the isbn of this book.
     * 
     * @param isbn this book's new isbn.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Get the list of reviews this book haves.
     * 
     * @return this book's reviews.
     */
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference(value = "reviewToBookReference")
    @JsonIgnore
    public Set<Review> getReviews() {
        return reviews;
    }

    /**
     * Changes the list of reviews for this book.
     * 
     * @param reviews this book's new list of reviews.
     */
    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Get the list of notifications this book haves.
     * 
     * @return this book's notifications.
     */
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference(value = "notificationToBookReference")
    @JsonIgnore
    public Set<Notification> getNotifications() {
        return notifications;
    }

    /**
     * Changes the notifications for this book.
     * 
     * @param notifications this book's new list of notifications.
     */
    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }
}
