package fhm.wi.team5.android_application;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;

import fhm.wi.team5.android_application.model.User;
import fhm.wi.team5.android_application.service.DownloadImageTask;

/**
 * Activity that displays the users information.
 *
 * @author Jan Schönfeld
 */
public class UserActivity extends NavigationDrawerActivity {

    User user;
    ListView userProperties;
    ArrayList<String> propertyList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        onCreateDrawer();
        navView.getMenu().getItem(1).setChecked(true);
        setTitle("Mein Profil");

        user = (User) getIntent().getExtras().get("user");
        userProperties = (ListView) findViewById(R.id.list_user_properties);

        String capName = WordUtils.capitalize(user.getUsername().replace(".", " "));
        propertyList = new ArrayList<>();
        propertyList.add("Benutzername:  " + capName);
        propertyList.add("E-Mail:                 " + user.getEmail());
        propertyList.add("Telefonnr. :         " + user.getPhoneNumber());


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, propertyList);
        userProperties.setAdapter(adapter);


        new DownloadImageTask((ImageView) findViewById(R.id.user_picture))
                .execute(user.getImg_url());


    }

    @Override
    public void onResume() {
        super.onResume();
        navView.getMenu().getItem(1).setChecked(true);
    }
}
