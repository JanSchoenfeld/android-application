package fhm.wi.team5.android_application;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import fhm.wi.team5.android_application.model.Bike;
import fhm.wi.team5.android_application.service.IApi;
import fhm.wi.team5.android_application.service.LocationService;
import fhm.wi.team5.android_application.service.SharedPreferencesService;
import fhm.wi.team5.android_application.service.UserAddressService;
import fhm.wi.team5.android_application.transfer.SearchRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static fhm.wi.team5.android_application.service.BaseUrl.BASE_URL;


/**
 * Main Activity where the user can send a search request to the server.
 *
 * @author Jan Schönfeld
 */
public class MainActivity extends NavigationDrawerActivity {

    private final String TAG = MainActivity.this.getClass().getSimpleName();
    Spinner spinner;
    Button searchBtn;
    Button mapsBtn;
    TextView resultText;
    double longitude;
    double latitude;
    ArrayAdapter<CharSequence> adapter;
    String type;
    Bike[] bikes;
    Response helpResponse;
    UserAddressService uService;
    private LocationService gps;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateDrawer();
        navView.getMenu().getItem(0).setChecked(true);
        spinner = (Spinner) findViewById(R.id.biketype_spinner);
        searchBtn = (Button) findViewById(R.id.btn_search);
        mapsBtn = (Button) findViewById(R.id.btn_goto_maps);
        resultText = (TextView) findViewById(R.id.search_result_text);
        uService = new UserAddressService();

        adapter = ArrayAdapter.createFromResource(this, R.array.bike_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = (String) parent.getItemAtPosition(position);
                resultText.setVisibility(View.INVISIBLE);
                mapsBtn.setVisibility(View.INVISIBLE);
                Log.d(TAG, type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    searchBikes();
                } catch (IOException ex) {
                    Log.e(TAG, "IOException");
                }
            }
        });

        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("bikes", bikes);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    /**
     * Method that send a SearchRequest to the server using Retrofit2.
     * If a successfull response comes back, make Button visible that leads to MapsActivity.
     *
     * @throws IOException in case of wrong Input Output.
     */
    public void searchBikes() throws IOException {


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                checkLocationPermission();
            }
            searchBikes();
            return;
        }



        mapsBtn.setVisibility(View.INVISIBLE);
        resultText.setVisibility(View.INVISIBLE);


        gps = new LocationService(MainActivity.this);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IApi api = retrofit.create(IApi.class);


        String token = SharedPreferencesService.retrieveStringFromSharedPreference("Authorization", getBaseContext());

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.d(TAG, "Latitude: " + Double.toString(latitude));
            Log.d(TAG, "Longitude: " + Double.toString(longitude));
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.setLatitude(latitude);
            searchRequest.setLongitude(longitude);
            searchRequest.setType(type.toUpperCase());


            Call<ResponseBody> response = api.search(token, searchRequest);
            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {

                        int answer = response.code();
                        Log.d(TAG, Integer.toString(answer));
                        helpResponse = response;
                        try {
                            String answerBody = response.body().string();
                            Log.d(TAG, "body " + answerBody);
                            Gson gson = new GsonBuilder().create();
                            bikes = gson.fromJson(answerBody, Bike[].class);

                            if (bikes != null) {


                                for (Bike bike : bikes) {
                                    uService.matchUserWithAddress(bike, getBaseContext());
                                }

                                if (bikes.length > 0) {
                                    if (bikes.length == 1) {
                                        resultText.setText("Wir haben ein Fahrrad in deiner Nähe gefunden!");
                                        resultText.setVisibility(View.VISIBLE);
                                        mapsBtn.setVisibility(View.VISIBLE);
                                    } else {
                                        resultText.setText("Wir haben " + Integer.toString(bikes.length) + " Fahrräder in deiner Nähe gefunden!");
                                        resultText.setVisibility(View.VISIBLE);
                                        mapsBtn.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    resultText.setText("Wir haben leider keine passenden Fahrräder gefunden");
                                    resultText.setVisibility(View.VISIBLE);
                                }

                            }

                        } catch (IOException ex) {
                            Log.e(TAG, "IOException");
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Es lief etwas falsch", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getBaseContext(), "Verbindung zum Server fehlgeschlagen", Toast.LENGTH_LONG).show();
                }
            });

        } else {
            gps.showSettingsAlert();
        }
    }


    public void checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);


        }
    }



    /**
     * Override this method to stop the gps service upon closing the activity.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gps != null) {
            gps.stopUsingGPS();
        }
    }

    /**
     * Override this method to set the corrosponding NavigationDrawer menupoint as set
     * upon resuming this activity.
     */
    @Override
    public void onResume() {
        super.onResume();
        navView.getMenu().getItem(0).setChecked(true);
    }
}