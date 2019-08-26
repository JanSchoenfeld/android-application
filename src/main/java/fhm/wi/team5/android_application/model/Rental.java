package fhm.wi.team5.android_application.model;

import java.io.Serializable;

/**
 * Rental class that is used to parse the necessary object from the servers json response.
 *
 * @author Jan Schönfeld
 */

public class Rental implements Serializable {

    //private Date endDate;
    private Bike bike;
    private User customer;
    private Rating rating;
    private String rentalState;

    public Rental() {
        super();
    }

    public Rental(Bike bike, User customer) {
        this.bike = bike;
        this.customer = customer;
        this.rentalState = "INITIATED";
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Bike getBike() {
        return this.bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public String getRentalState() {
        return rentalState;
    }

    public void setRentalState(String rentalState) {
        this.rentalState = rentalState;
    }

    /*
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    */
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

}