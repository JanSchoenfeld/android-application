package fhm.wi.team5.android_application.model;

/**
 * RentalState enum that is used to parse the necessary object from the servers json response.
 *
 * @author Jan Schönfeld
 */

public enum RentalState {

    INITIATED(0), ACTIVE(1), CANCELLED(2), RETURNED(3);

    private final int value;

    RentalState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
