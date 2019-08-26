package fhm.wi.team5.android_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fhm.wi.team5.android_application.model.Rental;
import fhm.wi.team5.android_application.service.IApi;
import fhm.wi.team5.android_application.service.SharedPreferencesService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static fhm.wi.team5.android_application.service.BaseUrl.BASE_URL;

/**
 * Activity to display the rentals where the current user is a customer.
 * On select list item, directs to BikeActivity.
 *
 * @author Jan Schönfeld
 */
public class MyRentalActivity extends NavigationDrawerActivity {

    private static final String TAG = "MyRentalActivity";
    Rental[] rentals;
    ListView list;
    ArrayAdapter<String> adapter;
    List<String> rentalList;
    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rental);
        onCreateDrawer();
        navView.getMenu().getItem(2).setChecked(true);
        list = (ListView) findViewById(R.id.rental_list);
        text = (TextView) findViewById(R.id.no_rental_message);
        getRentals();
        setTitle("Meine Ausleihen");
    }

    /**
     * Method that calls the server using Retrofit2 and stores the parsed json result into a variable "rentals".
     */
    protected void getRentals() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IApi api = retrofit.create(IApi.class);

        String token = SharedPreferencesService.retrieveStringFromSharedPreference("Authorization", getBaseContext());
        Call<ResponseBody> call = api.getRentalsOfCustomer(token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String serverResponse = response.body().string();
                        Log.d(TAG, serverResponse);
                        Gson gson = new GsonBuilder().create();
                        rentals = gson.fromJson(serverResponse, Rental[].class);

                        if (rentals != null && rentals.length != 0) {
                            Log.d(TAG, Integer.toString(rentals.length));
                            rentalList = new ArrayList<>();

                            for (Rental rental : rentals) {
                                rentalList.add(rental.getBike().getName());
                            }

                            //Collections.reverse(rentalList);
                            adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, rentalList);
                            list.setAdapter(adapter);
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    Intent intent = new Intent(MyRentalActivity.this, RentalActivity.class);
                                    intent.putExtra("rental", rentals[position]);
                                    intent.putExtra("previous", "myrental");
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                                }
                            });
                        } else {
                            list.setVisibility(View.GONE);
                            text.setVisibility(View.VISIBLE);
                        }

                        Toast.makeText(getBaseContext(), "Server call successful, created rentals", Toast.LENGTH_LONG).show();
                    } catch (IOException ex) {
                    }
                } else {
                    Log.d(TAG, Integer.toString(response.code()));
                    Toast.makeText(getBaseContext(), "Bad Request", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Verbindung zum Server fehlgeschlagen", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Override this method to set the corrosponding NavigationDrawer menupoint as set
     * upon resuming this activity.
     */
    @Override
    public void onResume() {
        super.onResume();
        navView.getMenu().getItem(2).setChecked(true);
    }
}
