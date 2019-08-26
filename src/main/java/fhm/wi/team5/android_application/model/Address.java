package fhm.wi.team5.android_application.model;

import java.io.Serializable;

/**
 * Address class that is used to parse the necessary object from the servers json response.
 *
 * @author Jan Schönfeld
 */

public class Address implements Serializable {
    private String street;
    private String houseNumber;
    private String zipCode;
    private String city;
    private String additionalInfo;

    private double longitude;
    private double latitude;

    public Address() {
        super();
    }

    public Address(String street, String houseNumber, String zipCode, String city, String additionalInfo) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.additionalInfo = additionalInfo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}
