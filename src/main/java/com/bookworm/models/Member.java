package com.bookworm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import org.springframework.hateoas.ResourceSupport;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representation of a member.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @since 1.7
 */
@Entity
@Table(name="member")
public class Member extends ResourceSupport {
    
    /**
     * Member's database id.
     */
    private long memberId;
    
    /**
     * Member's email.
     */
    private String email;
    
    /**
     * Member's username.
     */
    private String username;
    
    /**
     * Member's password.
     */
    private String password;
    
    /**
     * Member's list of reviews.
     */
    private Set<Review> reviews;
    
    /**
     * Member's list of notifications.
     */
    private Set<Notification> notifications;

    /**
     * Value that represent if this member is admin.
     */
    private boolean isAdmin = false;

    /**
     * Default constructor for Spring.
     */
    public Member() {
    }

    /**
     * Creates a new member with the given username, password and email.
     * 
     * @param username
     * @param password
     * @param email 
     */
    public Member(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Gets the database id of this member.
     * 
     * @return this book's id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getMemberId() {
        return memberId;
    }

    /**
     * Changes the id of this member.
     * 
     * @param memberId this member's new id.
     */
    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    /**
     * Gets the username of this member.
     * 
     * @return this member's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Changes the username of this member.
     * 
     * @param username this member's new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of this member.
     * 
     * @return this member's password.
     */
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    /**
     * Changes the password of this member.
     * 
     * @param password this member's new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Gets the email of this member.
     * 
     * @return this member's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Changes the email of this member.
     * 
     * @param email this member's new email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Changes the admin value of this member.
     * 
     * True - admin, false - not admin.
     * 
     * @param isAdmin 
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * Gets the username of this member.
     * 
     * @return this member's username.
     */
    @JsonIgnore
    @Transient
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Get the list of reviews of this member.
     * 
     * @return this member's review list.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference(value = "reviewToMemberReference")
    public Set<Review> getReviews() {
        return reviews;
    }

    /**
     * Changes the review list of this member.
     * 
     * @param reviews this member's new review list.
     */
    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
    
    /**
     * Get the list of notifications of this member.
     * 
     * @return this member's notification list.
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    //@JsonManagedReference(value = "notificationToMemberReference")
    @JsonIgnore
    public Set<Notification> getNotifications() {
        return notifications;
    }

    /**
     * Changes the notification list of this member.
     * 
     * @param notifications this member's new notification list.
     */
    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }
}
