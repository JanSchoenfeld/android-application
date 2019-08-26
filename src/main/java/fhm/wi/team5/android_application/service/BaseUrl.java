package fhm.wi.team5.android_application.service;

/**
 * Class to store the servers BASE_URL used in Retrofit
 *
 * @author Jan Schönfeld
 */

public final class BaseUrl {

    public static final String BASE_URL = "http://10.60.64.75/allride-server-web/api/v1/";

    private BaseUrl() {
        throw new IllegalAccessError("Utility class");
    }
}
