package fhm.wi.team5.android_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

import fhm.wi.team5.android_application.model.Rental;
import fhm.wi.team5.android_application.service.DownloadImageTask;

/**
 * Activity that displays a rentals information in a ListView.
 *
 * @author Jan Schönfeld
 */
public class RentalActivity extends NavigationDrawerActivity {

    Rental rental;
    ListView rentalProperties;
    List<String> propertyList;
    ArrayAdapter<String> adapter;
    String previous;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental);
        onCreateDrawer();
        setTitle("Ausleih Info");

        rental = (Rental) getIntent().getExtras().get("rental");
        previous = getIntent().getExtras().getString("previous");
        rentalProperties = (ListView) findViewById(R.id.rental_property_list);

        String capOwner = WordUtils.capitalize(rental.getBike().getUser().getUsername().replace(".", " "));
        String capCustomer = WordUtils.capitalize(rental.getCustomer().getUsername().replace(".", " "));
        String state = WordUtils.capitalize(rental.getRentalState().toLowerCase());

        propertyList = new ArrayList<>();
        propertyList.add("Radname:         " + rental.getBike().getName());
        if (("myrentals").equals(previous)) {
            propertyList.add("Besitzer:           " + capOwner);
        }
        if (("myrented").equals(previous)) {
            propertyList.add("Kunde:               " + capCustomer);
        }
        propertyList.add("Telefonnr. :       " + rental.getBike().getUser().getPhoneNumber());
        propertyList.add("Ausleihstatus:  " + state);
        if (("RETURNED").equals(rental.getRentalState()) && rental.getRating() != null) {
            propertyList.add("Bewertung:       " + rental.getRating().getStars() + " Sterne");
            propertyList.add("Kommentar:      " + rental.getRating().getComment());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, propertyList);
        rentalProperties.setAdapter(adapter);


        new DownloadImageTask((ImageView) findViewById(R.id.rental_picture))
                .execute(rental.getBike().getImageUrl());

        rentalProperties.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(RentalActivity.this, BikeActivity.class);
                    intent.putExtra("bike", rental.getBike());
                    intent.putExtra("previous", "rental");
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            }
        });
    }
}
