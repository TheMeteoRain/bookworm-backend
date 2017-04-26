/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bookworm.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Akash
 */
@Entity
@Table(name="purchase")
public class Purchase extends ResourceSupport {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long purchaseId;
    private long memberId;
    private long bookId;
    private int amount;

    public Purchase() {
    }

    public Purchase(long memberId, long bookId, int amount) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.amount = amount;
    }

    public long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    
}
