package fhm.wi.team5.android_application.transfer;

/**
 * @author Jan Schönfeld
 */

public class SignUpRequest {
    private String username;
    private String password;
    private String email;
    private String street;
    private String houseNumber;
    private String zipCode;
    private String city;
    private String additionalInfo;

    public SignUpRequest() {
        super();
    }

    public SignUpRequest(String name, String password, String email, String street, String houseNumber,
                         String zipCode, String city, String additionalInfo) {
        super();
        this.username = name;
        this.password = password;
        this.email = email;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }
}
