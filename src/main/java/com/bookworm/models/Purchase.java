package com.bookworm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.hateoas.ResourceSupport;

/**
 * Class that represents purchases.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @since 1.7
 */
@Entity
@Table(name="purchase")
public class Purchase extends ResourceSupport {
    
    /**
     * Purchase's database id.
     */
    private long purchaseId;
    
    /**
     * Number of bought items.
     */
    private int amount;
    
    /**
     * Purchase's member.
     */
    private Member member;
    
    /**
     * Purchase's book.
     */
    private Book book;

    /**
     * Default constructor for Spring.
     */
    public Purchase() {}

    /**
     * Creates a new purchase with the given member, book and number of bought
     * books.
     * 
     * @param member
     * @param book
     * @param amount 
     */
    public Purchase(Member member, Book book, int amount) {
        this.amount = amount;
        this.member = member;
        this.book = book;
    }

    /**
     * Gets the database id of this purchase.
     * 
     * @return this purchase's id.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long getPurchaseId() {
        return purchaseId;
    }

    /**
     * Changes the id of this purchase.
     * 
     * @param purchaseId this purchase's new id.
     */
    public void setPurchaseId(long purchaseId) {
        this.purchaseId = purchaseId;
    }
    
    /**
     * Gets the amount of this purchase.
     * 
     * @return this purchase's amount.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Changes the amount of this purchase.
     * 
     * @param amount this purchase's new amount.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    
    /**
     * Gets the member of this purchase.
     * 
     * @return this purchase's member.
     */
    @ManyToOne
    @JoinColumn(name = "memberId")
    public Member getMember() {
        return member;
    }

    /**
     * Changes the member of this purchase.
     * 
     * @param member this purchase's new member.
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * Gets the book of this purchase.
     * 
     * @return this purchase's book.
     */
    @ManyToOne
    @JoinColumn(name = "bookId")
    public Book getBook() {
        return book;
    }

    /**
     * Changes the book of this purchase.
     * 
     * @param book this purchase's new book.
     */
    public void setBook(Book book) {
        this.book = book;
    }
}
