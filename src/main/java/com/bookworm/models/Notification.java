package com.bookworm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import org.springframework.hateoas.ResourceSupport;

/**
 * Class representation of member's notification.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @since 1.7
 */
@Entity
@Table(name = "notification")
@IdClass(NotificationId.class)
public class Notification extends ResourceSupport {

    /**
     * Book where this notification is left on.
     */
    private Book book;
    
    /**
     * Notifiable member.
     */
    private Member member;

    /**
     * Default constructor for Spring.
     */
    public Notification() {}

    /**
     * Creates a new notification with the given book and member.
     * 
     * @param book
     * @param member 
     */
    public Notification(Book book, Member member) {
        this.book = book;
        this.member = member;
    }

    /**
     * Gets the member of this notification.
     * 
     * @return this notification's member.
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
   // @JsonBackReference(value = "notificationToMemberReference")
    public Member getMember() {
        return member;
    }

    /**
     * Changes the member of this notification.
     * 
     * @param member this member's new notification.
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * Gets the book of this notification.
     * 
     * @return this notification's book.
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "bookId", referencedColumnName = "bookId")
   // @JsonBackReference(value = "notificationToBookReference")
    @JsonIgnore
    public Book getBook() {
        return book;
    }

    /**
     * Changes the book of this notification.
     * 
     * @param book this book's new notification.
     */
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

    /**
     * Compare notifications book and member.
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
        Notification other = (Notification) obj;
        if (book.getBookId() != other.book.getBookId())
            return false;
        if (member.getMemberId() != other.member.getMemberId())
            return false;
        return true;
    }
}
