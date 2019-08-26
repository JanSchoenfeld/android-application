package fhm.wi.team5.android_application.model;

import java.io.Serializable;

/**
 * User class that is used to parse the necessary object from the servers json response.
 *
 * @author Jan Schönfeld
 */

public class User implements Serializable {

    private long id;
    private String username;
    private String img_url;
    private String email;
    private String name;
    private Address address;
    private String phoneNumber;


    public User() {
    }

    public User(long id, String username, String img_url, String email, String phoneNumber, String name) {
        this.id = id;
        this.username = username;
        this.img_url = img_url;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}