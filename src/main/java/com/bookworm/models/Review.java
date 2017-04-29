package com.bookworm.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by tonis on 2017-04-25.
 */
@Entity
@Table(name = "review")
public class Review extends ResourceSupport implements Serializable {

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
    @JoinColumn(name = "bookId", referencedColumnName = "bookId")
    @JsonBackReference(value = "reviewToBookReference")
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
    @JsonBackReference(value = "reviewToMemberReference")
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
        result = prime * result + book.getBookId();
        result = prime * result + member.getMemberId();
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
        if (book.getBookId()!= other.book.getBookId())
            return false;
        if (member.getMemberId()!= other.member.getMemberId())
            return false;
        return true;
    }
}
