package fhm.wi.team5.android_application.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

/**
 * Service class that uses the devices GPS/Network-Connection to get the current location.
 *
 * @author Jan Schönfeld
 */

public class LocationService extends Service implements LocationListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000L * 60;
    private final Context context;
    protected LocationManager locationManager;
    boolean checkGPS = false;
    boolean checkNetwork = false;
    boolean canGetLocation = false;
    Location location;
    double latitude;
    double longitude;

    public LocationService(Context context) {
        this.context = context;
        getLocation();
    }

    private Location getLocation() throws SecurityException {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        checkNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!checkGPS && !checkNetwork) {
            Toast.makeText(context, "Kein Standortdienst verfügbar", Toast.LENGTH_LONG).show();
        } else {
            this.canGetLocation = true;
            if (checkNetwork) {
                Log.d("MainActivity", "Suche über Network Provider");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            }

            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
            if (checkGPS) {
                Log.d("MainActivity", "Suche über GPS Provider");
                if (location == null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }
        }
        return location;
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean canGetLocation() {
        return canGetLocation;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);


        alertDialog.setTitle("GPS nicht aktiviert");

        alertDialog.setMessage("Möchtest du GPS aktivieren?");


        alertDialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(LocationService.this);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location loc) {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }
}

