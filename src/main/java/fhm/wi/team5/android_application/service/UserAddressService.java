package fhm.wi.team5.android_application.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import fhm.wi.team5.android_application.model.Address;
import fhm.wi.team5.android_application.model.Bike;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static fhm.wi.team5.android_application.service.BaseUrl.BASE_URL;

/**
 * Class to store the retrofit call which matches a user with his corrosponding address
 *
 * @author Jan Schönfeld
 */

public class UserAddressService {

    private final String TAG = this.getClass().getSimpleName();

    public void matchUserWithAddress(final Bike bike, final Context context) {

        String token = SharedPreferencesService.retrieveStringFromSharedPreference("Authorization", context);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IApi api = retrofit.create(IApi.class);

        Call<ResponseBody> response = api.getUserAddress(bike.getUser().getId(), token);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String answerString = response.body().string();
                        Log.d(TAG, answerString);

                        Gson gson = new GsonBuilder().create();
                        Address address = gson.fromJson(answerString, Address.class);
                        bike.getUser().setAddress(address);
                        Log.d(TAG, Double.toString(address.getLatitude()));
                        Log.d(TAG, Double.toString(address.getLongitude()));

                    } catch (IOException ex) {

                    }
                } else {

                    Log.d(TAG, Integer.toString(response.code()));
                    Toast.makeText(context, "etwas lief falsch", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Verbindung zum Server fehlgeschlagen", Toast.LENGTH_LONG).show();
            }
        });

    }
}