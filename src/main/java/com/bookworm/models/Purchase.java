/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Akash
 */
@Entity
@Table(name="purchase")
public class Purchase extends ResourceSupport {
    
    private long purchaseId;
    private int amount;
    private Member member;
    private Book book;

    public Purchase() {
    }

    public Purchase(Member member, Book book, int amount) {
        this.amount = amount;
        this.member = member;
        this.book = book;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(long purchaseId) {
        this.purchaseId = purchaseId;
    }
    
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @ManyToOne
    @JoinColumn(name = "memberId")
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @ManyToOne
    @JoinColumn(name = "bookId")
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
