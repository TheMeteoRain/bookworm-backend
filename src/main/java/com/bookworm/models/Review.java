package com.bookworm.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tonis on 2017-04-25.
 */
@Entity
@Table(name = "review")
public class Review implements Serializable {

    private Book book;
    private Member member;
    private String text;
    private double stars;

    public Review() {
    }

    public Review(Book book, Member member, String text, double stars) {
        this.book = book;
        this.member = member;
        this.text = text;
        this.stars = stars;
    }


    @Id
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    @JsonBackReference
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @JsonBackReference
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        long result = 1;
        result = prime * result + book.getId();
        result = prime * result + member.getId();
        return (int)result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Review other = (Review) obj;
        if (book.getId() != other.book.getId())
            return false;
        if (member.getId() != other.member.getId())
            return false;
        return true;
    }
}
