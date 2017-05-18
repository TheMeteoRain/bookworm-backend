/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookworm.models;

import java.io.Serializable;

/**
 *
 * @author Akash
 */
public class NotificationId implements Serializable {
    private long book;
    private long member;

    public NotificationId() {
    }

    public NotificationId(long book, long member) {
        this.book = book;
        this.member = member;
    }

    public long getMember() {
        return member;
    }

    public long getBook() {
        return book;
    }

    public void setMember(long member) {
        this.member = member;
    }

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
