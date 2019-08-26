package fhm.wi.team5.android_application;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;

import fhm.wi.team5.android_application.model.Bike;

import static fhm.wi.team5.android_application.R.id.map;

/**
 * Activity containing the GoogleMaps fragment to display the found bikes.
 *
 * @author Jan Schönfeld
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private final String TAG = MapsActivity.this.getClass().getSimpleName();

    ListView list;
    Bike bike;
    Bike[] bikes;
    double latitude;
    double longitude;
    ArrayList<String> bikeList;
    ArrayAdapter<String> adapter;
    int bikeId;
    Button gotoBike;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        list = (ListView) findViewById(R.id.list);
        gotoBike = (Button) findViewById(R.id.btn_goto_bike);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        latitude = getIntent().getExtras().getDouble("latitude");
        longitude = getIntent().getExtras().getDouble("longitude");
        bikes = (Bike[]) getIntent().getExtras().get("bikes");


        bikeList = new ArrayList<>();
        for (Bike bike : bikes) {
            bikeList.add(bike.getName());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bikeList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                moveCameraToBike(id);
            }
        });

        gotoBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Bike bike : bikes) {
                    Log.d(TAG, bike.getName());
                }
                bike = getBike(bikes, bikeId);
                Log.d(TAG, bike.toString());
                Intent intent = new Intent(MapsActivity.this, BikeActivity.class);
                intent.putExtra("bike", bike);
                intent.putExtra("previous", "maps");
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng userLocation = new LatLng(latitude, longitude);

        createAndShowMarker(userLocation, bikes);

        mMap.addMarker(new MarkerOptions().position(userLocation).title("Deine Position").snippet("Du bist hier.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        Log.d(TAG, userLocation.toString());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14.5f));
    }

    /**
     * Method to display location markers on the map using the found bikes locations and
     * draw lines between userLocation and the bikes.
     *
     * @param userLocation LatLng that displays the users location.
     * @param bikes        Array of bikes that have been found in the previous activity.
     */
    public void createAndShowMarker(LatLng userLocation, Bike[] bikes) {
        if (bikes.length == 3) {
            LatLng bike1 = new LatLng(bikes[0].getUser().getAddress().getLatitude(), bikes[0].getUser().getAddress().getLongitude());

            mMap.addMarker(new MarkerOptions()
                    .position(bike1)
                    .title("Fahrrad von " + WordUtils.capitalize(bikes[0].getUser().getUsername().replace(".", " ")))
                    .snippet(bikes[0].getUser().getAddress().getStreet() + " " + bikes[0].getUser().getAddress().getHouseNumber())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

            LatLng bike2 = new LatLng(bikes[1].getUser().getAddress().getLatitude(), bikes[1].getUser().getAddress().getLongitude());

            mMap.addMarker(new MarkerOptions()
                    .position(bike2)
                    .title("Fahrrad von " + WordUtils.capitalize(bikes[1].getUser().getUsername().replace(".", " ")))
                    .snippet(bikes[1].getUser().getAddress().getStreet() + " " + bikes[1].getUser().getAddress().getHouseNumber())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

            LatLng bike3 = new LatLng(bikes[2].getUser().getAddress().getLatitude(), bikes[2].getUser().getAddress().getLongitude());

            mMap.addMarker(new MarkerOptions()
                    .position(bike3)
                    .title("Fahrrad von " + WordUtils.capitalize(bikes[2].getUser().getUsername().replace(".", " ")))
                    .snippet(bikes[2].getUser().getAddress().getStreet() + " " + bikes[2].getUser().getAddress().getHouseNumber())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

            PolylineOptions line =
                    new PolylineOptions().add(userLocation, bike1)
                            .add(userLocation, bike2)
                            .add(userLocation, bike3)
                            .width(5).color(Color.BLUE);
            mMap.addPolyline(line);

        } else if (bikes.length == 2) {

            LatLng bike1 = new LatLng(bikes[0].getUser().getAddress().getLatitude(), bikes[0].getUser().getAddress().getLongitude());

            mMap.addMarker(new MarkerOptions()
                    .position(bike1)
                    .title("Fahrrad von " + WordUtils.capitalize(bikes[0].getUser().getUsername().replace(".", " ")))
                    .snippet(bikes[0].getUser().getAddress().getStreet() + " " + bikes[0].getUser().getAddress().getHouseNumber())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

            LatLng bike2 = new LatLng(bikes[1].getUser().getAddress().getLatitude(), bikes[1].getUser().getAddress().getLongitude());

            mMap.addMarker(new MarkerOptions()
                    .position(bike2)
                    .title("Fahrrad von " + WordUtils.capitalize(bikes[1].getUser().getUsername().replace(".", " ")))
                    .snippet(bikes[1].getUser().getAddress().getStreet() + " " + bikes[1].getUser().getAddress().getHouseNumber())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

            PolylineOptions line =
                    new PolylineOptions().add(userLocation, bike1)
                            .add(userLocation, bike2)
                            .width(5).color(Color.BLUE);
            mMap.addPolyline(line);


        } else if (bikes.length == 1) {

            LatLng bike1 = new LatLng(bikes[0].getUser().getAddress().getLatitude(), bikes[0].getUser().getAddress().getLongitude());

            mMap.addMarker(new MarkerOptions()
                    .position(bike1)
                    .title("Fahrrad von " + WordUtils.capitalize(bikes[0].getUser().getUsername().replace(".", " ")))
                    .snippet(bikes[0].getUser().getAddress().getStreet() + " " + bikes[0].getUser().getAddress().getHouseNumber())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

            PolylineOptions line =
                    new PolylineOptions().add(userLocation, bike1)
                            .width(5).color(Color.BLUE);
            mMap.addPolyline(line);

        }
    }

    /**
     * Method to center the camera on a bike upon clicking it.
     *
     * @param id contains the list id of the bike to be centered
     */
    public void moveCameraToBike(long id) {
        int i = (int) id;
        this.bikeId = i;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(bikes[i].getUser().getAddress().getLatitude(), bikes[i].getUser().getAddress().getLongitude())));
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public Bike getBike(Bike[] bikes, int index) {
        return bikes[index];
    }
}
