package fhm.wi.team5.android_application.model;

import java.io.Serializable;


/**
 * Bike class that is used to parse the necessary object from the servers json response.
 *
 * @author Jan Schönfeld
 */

public class Bike implements Serializable {
    private long id;
    private String name;
    private double price;
    private String imageUrl;
    private boolean deleted;
    private boolean locked;
    private String type;
    private User user;

    public Bike() {
    }

    public Bike(long id, String name, double price, String imageUrl, boolean deleted, boolean locked, String type, User user) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.deleted = deleted;
        this.locked = locked;
        this.type = type;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
