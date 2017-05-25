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
 * Class representation of book's author.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @since 1.7
 */
@Entity
@Table(name="author")
public class Author extends ResourceSupport {
    
    /**
     * Author's database id.
     */
    private long authorId;
    
    /**
     * Author's first name.
     */
    private String firstName;
    
    /**
     * Authors' last name.
     */
    private String lastName;
    
    /**
     * List of books that author has written.
     */
    private List<Book> bookAuthor = new ArrayList<>();
    
    /**
     * Default constructor for Spring.
     */
    public Author() {}

    /**
     * Creates a new author with the given first and last names.
     * 
     * @param firstName
     * @param lastName 
     */
    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Gets the database id of this author.
     * 
     * @return this author's id.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long getAuthorId() {
        return authorId;
    }

    /**
     * Changes the id of this author.
     * 
     * @param authorId this author's new id.
     */
    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    /**
     * Gets the first name of this author.
     * 
     * @return this author's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Changes the first name of this author.
     * 
     * @param firstName this author's new first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of this author.
     * 
     * @return this author's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Changes the last name of this author.
     * 
     * @param lastName this author's new last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the list of books this author has written.
     * 
     * @return this author's book list.
     */
    @ManyToMany(mappedBy = "authors")
    @JsonBackReference(value = "authorToBookReference")
    public List<Book> getBookAuthor() {
        return new ArrayList<>(bookAuthor);

    }

    /**
     * Changes the book list of this author.
     * 
     * @param books this author's new book list.
     */
    public void setBookAuthor(List<Book> books) {
        this.bookAuthor = new ArrayList<>(books);
    }
    
    /**
     * Add a book to this author's book list.
     * 
     * @param book book to be added to the list.
     */
    public void addBookAuthor(Book book){
        if(! bookAuthor.contains(book)){
            bookAuthor.add(book);
            
            book.addAuthor(this);
        }
    }

    /**
     * Removes a book from this author's book list.
     * 
     * @param book book to be removed from the list.
     */
    public void removeBookAuthor(Book book){
        if(bookAuthor.contains(book)){
            bookAuthor.remove(book);
            
            book.removeAuthor(this);
        }
    }
}
