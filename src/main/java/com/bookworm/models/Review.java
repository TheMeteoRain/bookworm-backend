package com.bookworm.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class representation of review.
 * 
 * @version 2017.0522
 * @author Toni Seppalainen toni.seppalainen@cs.tamk.fi
 * @since 1.7
 */
@Entity
@Table(name = "review")
public class Review extends ResourceSupport implements Serializable {

    /**
     * Book that has this review.
     */
    private Book book;
    
    /**
     * Member who has written this review.
     */
    private Member member;
    
    /**
     * Review text.
     */
    private String text;
    
    /**
     * Review stars.
     */
    private double stars;

    /**
     * Default constructor for Spring.
     */
    public Review() {}

    /**
     * Creates a new review with the given book, member, text and stars.
     * 
     * @param book
     * @param member
     * @param text
     * @param stars 
     */
    public Review(Book book, Member member, String text, double stars) {
        this.book = book;
        this.member = member;
        this.text = text;
        this.stars = stars;
    }


    /**
     * Gets the book of this review.
     * 
     * @return this review's book.
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "bookId", referencedColumnName = "bookId")
   // @JsonBackReference(value = "reviewToBookReference")
    @JsonIgnore
    public Book getBook() {
        return book;
    }

    /**
     * Changes the book of this review.
     * 
     * @param book this review's new book.
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Gets the member of this review.
     * 
     * @return this review's member.
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
    //@JsonBackReference(value = "reviewToMemberReference")
    public Member getMember() {
        return member;
    }

    /**
     * Changes the member of this review.
     * 
     * @param member this review's new member.
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * Gets the text of this review.
     * 
     * @return this review's text.
     */
    public String getText() {
        return text;
    }

    /**
     * Changes the text of this review.
     * 
     * @param text this review's new text.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the stars of this review.
     * 
     * @return this review's stars.
     */
    public double getStars() {
        return stars;
    }

    /**
     * Changes the stars of this review.
     * 
     * @param stars this review's new stars.
     */
    public void setStars(double stars) {
        this.stars = stars;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        long result = 1;
        result = prime * result + book.getBookId();
        result = prime * result + member.getMemberId();
        return (int)result;
    }

    /**
     * Compare reviews book and member.
     * 
     * @param obj
     * @return true, if both book and member are the same.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Review other = (Review) obj;
        if (book.getBookId()!= other.book.getBookId())
            return false;
        if (member.getMemberId()!= other.member.getMemberId())
            return false;
        return true;
    }
}
