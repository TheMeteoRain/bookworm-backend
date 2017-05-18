package com.bookworm.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tonis on 2017-04-25.
 */
@Entity
@Table(name = "notification")
@IdClass(NotificationId.class)
public class Notification extends ResourceSupport {

    private Book book;
    private Member member;

    public Notification() {
    }

    public Notification(Book book, Member member) {
        this.book = book;
        this.member = member;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
   // @JsonBackReference(value = "notificationToMemberReference")
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "bookId", referencedColumnName = "bookId")
   // @JsonBackReference(value = "notificationToBookReference")
    @JsonIgnore
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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
        Notification other = (Notification) obj;
        if (book.getBookId() != other.book.getBookId())
            return false;
        if (member.getMemberId() != other.member.getMemberId())
            return false;
        return true;
    }
}
