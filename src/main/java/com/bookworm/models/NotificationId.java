package com.bookworm.models;

import java.io.Serializable;

/**
 * Class that identifies notification.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @since 1.7
 */
public class NotificationId implements Serializable {
    
    /**
     * Book's id.
     */
    private long book;
    
    /**
     * Member's id.
     */
    private long member;

    /**
     * Default constructor for Spring.
     */
    public NotificationId() {}

    /**
     * Creates a new notificaitonId with the given book and member id.
     * 
     * @param book
     * @param member 
     */
    public NotificationId(long book, long member) {
        this.book = book;
        this.member = member;
    }

    /**
     * Gets the member id of this notificationId.
     * 
     * @return this notificatonId's member id.
     */
    public long getMember() {
        return member;
    }

    /**
     * Gets the book id of this notificationId.
     * 
     * @return this notificatonId's book id.
     */
    public long getBook() {
        return book;
    }

    /**
     * Changes the member id of this notificationId.
     * 
     * @param member this notificationId's new member id.
     */
    public void setMember(long member) {
        this.member = member;
    }

    /**
     * Changes the book id of this notificationId.
     * 
     * @param book this notificationId's new book id.
     */
    public void setBook(long book) {
        this.book = book;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        long result = 1;
        result = prime * result + book;
        result = prime * result + member;
        return (int)result;
    }

    /**
     * Compare notificationIds book and member ids.
     * 
     * @param obj
     * @return true, if both book and member ids are the same.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NotificationId other = (NotificationId) obj;
        if (book != other.book)
            return false;
        if (member != other.member)
            return false;
        return true;
    }
}
