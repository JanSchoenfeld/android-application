package fhm.wi.team5.android_application.transfer;

/**
 * @author Jan Schönfeld
 */

public class LoginRequest {
    private String email;
    private String password;

    public LoginRequest() {
        super();
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}