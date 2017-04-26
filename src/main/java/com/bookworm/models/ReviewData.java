package com.bookworm.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tonis on 2017-04-25.
 */
public class ReviewData {

    private String text;
    private double stars;

    public ReviewData() {
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
