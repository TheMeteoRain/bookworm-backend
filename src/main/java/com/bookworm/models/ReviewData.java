package com.bookworm.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Class representation of review data.
 * 
 * @version 2017.0522
 * @author Toni Seppalainen toni.seppalainen@cs.tamk.fi
 * @since 1.7
 */
public class ReviewData {

    /**
     * ReviewData's text.
     */
    @NotBlank(message = "Review text may not be empty")
    private String text;
    
    /**
     * ReviewData's stars.
     */
    @Max(value = 5, message = "Review stars cannot be higher than 5")
    @Min(value = 0, message = "Review stars cannot be smaller than 0")
    private double stars;

    /**
     * Default constructor for Spring.
     */
    public ReviewData() {}

    /**
     * Gets the text of this review data.
     * 
     * @return this review data's text.
     */
    public String getText() {
        return text;
    }

    /**
     * Changes the text of this review data.
     * 
     * @param text this review data's new text.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the stars of this review data.
     * 
     * @return this review data's stars.
     */
    public double getStars() {
        return stars;
    }

    /**
     * Changes the stars of this review data.
     * 
     * @param stars this review data's new stars.
     */
    public void setStars(double stars) {
        this.stars = stars;
    }
}
