package fhm.wi.team5.android_application.model;

import java.io.Serializable;

/**
 * Rating class that is used to parse the necessary object from the servers json response.
 *
 * @author Jan Schönfeld
 */

public class Rating implements Serializable {
    private Integer stars;
    private String comment;

    public Rating() {
    }

    public Rating(Integer stars, String comment) {
        this.stars = stars;
        this.comment = comment;
    }

    public Rating(String comment, Integer stars) {
        this.comment = comment;
        this.stars = stars;
    }

    public Integer getStars() {
        return stars;
    }

    public String getComment() {
        return comment;
    }

}