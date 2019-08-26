package fhm.wi.team5.android_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import fhm.wi.team5.android_application.model.User;
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
 * Superclass that has to be extended if an Activity wants to use the NavigationDrawer.
 *
 * @author Jan Schönfeld
 */
public class NavigationDrawerActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navView;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
    }

    /**
     * Method that initiates the drawer and sets the OnClickEvents.
     * Should be called in onCreate() of the Activity that extends it.
     */
    protected void onCreateDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        navView = (NavigationView) findViewById(R.id.nav_view);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.logout):

                        SharedPreferencesService.removeFromSharedPreferences("Authorization", getBaseContext());
                        SharedPreferencesService.removeFromSharedPreferences("userId", getBaseContext());
                        SharedPreferencesService.removeFromSharedPreferences("hasLoggedIn", getBaseContext());

                        Intent logoutIntent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(logoutIntent);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        finish();
                        break;

                    case (R.id.my_rentals):
                        Intent rentalIntent = new Intent(getBaseContext(), MyRentalActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(rentalIntent);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        break;

                    case (R.id.nav_search):
                        Intent searchIntent = new Intent(getBaseContext(), MainActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(searchIntent);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        break;

                    case (R.id.my_rented):
                        Intent rentedIntent = new Intent(getBaseContext(), MyRentedActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(rentedIntent);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        break;

                    case (R.id.my_profile):
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        IApi api = retrofit.create(IApi.class);

                        String token = SharedPreferencesService.retrieveStringFromSharedPreference("Authorization", getBaseContext());
                        long id = SharedPreferencesService.retrieveLongFromSharedPreference("userId", getBaseContext());

                        Call<ResponseBody> call = api.getUser(token, id);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    try {
                                        String serverResponse = response.body().string();
                                        Log.d("Server Antwort: ", serverResponse);
                                        Gson gson = new GsonBuilder().create();
                                        user = gson.fromJson(serverResponse, User.class);
                                        if (user != null) {
                                            Intent intent = new Intent(getBaseContext(), UserActivity.class);
                                            intent.putExtra("user", user);
                                            drawerLayout.closeDrawer(GravityCompat.START);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                        }

                                    } catch (IOException ex) {
                                    }
                                } else {
                                    Toast.makeText(getBaseContext(), "Server call successful, bad response", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getBaseContext(), "Couldn't connect to the server, check your connection", Toast.LENGTH_LONG).show();
                            }
                        });
                }

                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
