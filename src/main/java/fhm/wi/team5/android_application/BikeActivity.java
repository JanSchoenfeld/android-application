package fhm.wi.team5.android_application;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.text.WordUtils;

import java.io.IOException;
import java.util.ArrayList;

import fhm.wi.team5.android_application.model.Bike;
import fhm.wi.team5.android_application.model.Rental;
import fhm.wi.team5.android_application.service.DownloadImageTask;
import fhm.wi.team5.android_application.service.IApi;
import fhm.wi.team5.android_application.service.SharedPreferencesService;
import fhm.wi.team5.android_application.transfer.RentalRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static fhm.wi.team5.android_application.service.BaseUrl.BASE_URL;

/**
 * Activity to show a bikes information as ListView
 *
 * @author Jan Schönfeld
 */
public class BikeActivity extends NavigationDrawerActivity {

    private static final String TAG = "BikeActivity";

    Bike bike;
    ListView bikeProperties;
    ArrayList<String> propertyList;
    ArrayAdapter<String> adapter;
    Button rentalBtn;
    RentalRequest request;
    Rental rental;
    String previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);
        onCreateDrawer();
        setTitle("Rad Info");

        bike = (Bike) getIntent().getExtras().get("bike");
        previous = getIntent().getExtras().getString("previous");
        bikeProperties = (ListView) findViewById(R.id.text_bike_name);
        rentalBtn = (Button) findViewById(R.id.btn_goto_rental);
        if (("maps").equals(previous)) {
            rentalBtn.setVisibility(View.VISIBLE);
        }

        String capName = WordUtils.capitalize(bike.getUser().getUsername().replace(".", " "));
        String capType = WordUtils.capitalize(bike.getType().toLowerCase());
        propertyList = new ArrayList<>();
        propertyList.add("Radname:    " + bike.getName());
        propertyList.add("Besitzer:      " + capName);
        propertyList.add("Telefonnr.:   " + bike.getUser().getPhoneNumber());
        propertyList.add("Typ:              " + capType);
        if (bike.getUser().getAddress() != null) {
            propertyList.add("Straße:         " + bike.getUser().getAddress().getStreet() + " " + bike.getUser().getAddress().getHouseNumber());
            propertyList.add("Stadt:           " + bike.getUser().getAddress().getCity());
            propertyList.add("PLZ:             " + bike.getUser().getAddress().getZipCode());
            propertyList.add("Preis:           " + Double.toString(bike.getPrice()).replace(".", ",") + "€");
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, propertyList);
        bikeProperties.setAdapter(adapter);

        new DownloadImageTask((ImageView) findViewById(R.id.bike_picture))
                .execute(bike.getImageUrl());

        rentalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Rent Methode aufgerufen");
                rent();
                Intent intent = new Intent(BikeActivity.this, MyRentalActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });

    }


    /**
     * Method to initiate a rental using retrofit and calls new MyRentalActivity if successful
     */
    public void rent() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IApi api = retrofit.create(IApi.class);

        final ProgressDialog progressDialog = new ProgressDialog(BikeActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        String token = SharedPreferencesService.retrieveStringFromSharedPreference("Authorization", getBaseContext());
        request = new RentalRequest();
        request.setBikeId(bike.getId());
        Log.d(TAG, "Rad ID: " + request.getBikeId());
        request.setCustomerId(SharedPreferencesService.retrieveLongFromSharedPreference("userId", getBaseContext()));
        Log.d(TAG, "User ID: " + request.getCustomerId());
        request.setOwnerId(bike.getUser().getId());
        Log.d(TAG, "Besitzer ID: " + request.getOwnerId());

        Call<ResponseBody> call = api.addRental(token, request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    try {
                        String serverResponse = response.body().string();
                        Gson gson = new GsonBuilder().create();
                        Log.d(TAG, serverResponse);
                        rental = gson.fromJson(serverResponse, Rental.class);
                        Intent intent = new Intent(BikeActivity.this, MyRentalActivity.class);
                        startActivity(intent);

                    } catch (IOException ignored) {
                    }
                } else {
                    progressDialog.dismiss();
                    Log.d(TAG, Integer.toString(response.code()));
                    Toast.makeText(getBaseContext(), "Bad Request", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getBaseContext(), "Verbindung zum Server fehlgeschlagen", Toast.LENGTH_LONG).show();
            }
        });


    }
}
