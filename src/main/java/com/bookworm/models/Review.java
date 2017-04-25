package com.bookworm.models;

import javax.persistence.*;

/**
 * Created by tonis on 2017-04-25.
 */
@Entity
@Table(name="review")
public class Review {

    private Book book;
    private Member member;
    private String text;
    private double stars;

    @Id
    @ManyToOne
    @JoinColumn(name = "book_id")
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "member_id")
    public Member getMember() {
        return member;
    }

    public void setMember(Member publisher) {
        this.member = member;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }
}
