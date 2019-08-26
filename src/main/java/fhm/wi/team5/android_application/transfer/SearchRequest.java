package fhm.wi.team5.android_application.transfer;


/**
 * @author Jan Schönfeld
 */
public class SearchRequest {

    double latitude;
    double longitude;
    String type;

    public SearchRequest(double latitude, double longitude, String type) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    public SearchRequest() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longtitude) {
        this.longitude = longtitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
