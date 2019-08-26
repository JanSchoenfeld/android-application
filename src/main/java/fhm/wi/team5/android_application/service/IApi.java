package fhm.wi.team5.android_application.service;

import fhm.wi.team5.android_application.transfer.LoginRequest;
import fhm.wi.team5.android_application.transfer.RentalRequest;
import fhm.wi.team5.android_application.transfer.SearchRequest;
import fhm.wi.team5.android_application.transfer.SignUpRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Interface that offers methods for interaction with our wildfly-server
 *
 * @author Jan Schönfeld
 */

public interface IApi {

    /**
     * Method to sign up a user into our database
     *
     * @param request The request object containing the sign-up data
     * @return ResponseBody with the servers response
     */
    @POST("signup")
    Call<ResponseBody> signUp(@Body SignUpRequest request);

    /**
     * Method to login a user into our server
     *
     * @param request The request object containing the login data
     * @return ResponseBody with the servers response
     */
    @POST("login")
    Call<ResponseBody> login(@Body LoginRequest request);

    /**
     * Method to search bikes using the servers algorithm
     *
     * @param token         Authorization token to validate the user-level request with the server, sent inside the header
     * @param searchRequest Contains the Latitude & Longitude and Type for the search algorithm
     * @return ResponseBody containing the servers answer
     */
    @POST("algo/search")
    Call<ResponseBody> search(@Header("Authorization") String token, @Body SearchRequest searchRequest);


    /**
     * Method to get a User object from the server using the unique id.
     *
     * @param token Authorization token to validate the user-level request with the server, sent inside the header
     * @param id    Value used to get the user from the servers database
     * @return ResponseBody containing the servers response
     */
    @GET("user/{id}")
    Call<ResponseBody> getUser(@Header("Authorization") String token, @Path("id") long id);


    /**
     * Method to get the users address, which is set to @jsonignore server-side.
     *
     * @param id    Users id used to get corrosponding address object
     * @param token Authorization token to validate the user-level request with the server, sent inside the header
     * @return ResponseBody containing the servers response
     */
    @GET("user/{id}/address")
    Call<ResponseBody> getUserAddress(@Path("id") long id, @Header("Authorization") String token);

    /**
     * Method to initiate a rental
     *
     * @param token   Authorization token to validate the user-level request with the server, sent inside the header
     * @param request RentalRequest containing the needed information to initiate a rental
     * @return ResponseBody containing the new rental information
     */
    @POST("rental")
    Call<ResponseBody> addRental(@Header("Authorization") String token, @Body RentalRequest request);

    /**
     * Method to return all rentals where the current user is customer
     *
     * @param token Authorization token to validate the user-level request with the server, sent inside the header
     * @return ResponseBody / List containing the users rentals in which he is customer
     */
    @GET("user/rental/customer")
    Call<ResponseBody> getRentalsOfCustomer(@Header("Authorization") String token);

    /**
     * Method to return all rentals where the current user is owner
     *
     * @param token Authorization token to validate the user-level request with the server, sent inside the header
     * @return ResponseBody / List containing the users rentals in which he is owner
     */
    @GET("user/rental/owner")
    Call<ResponseBody> getRentalsOfOwner(@Header("Authorization") String token);


}
