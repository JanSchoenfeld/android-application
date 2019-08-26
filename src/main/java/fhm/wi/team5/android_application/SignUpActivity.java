package fhm.wi.team5.android_application;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import fhm.wi.team5.android_application.service.IApi;
import fhm.wi.team5.android_application.transfer.SignUpRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static fhm.wi.team5.android_application.service.BaseUrl.BASE_URL;


/**
 * Activity to offers user-interface and logic for a user sign-up.
 *
 * @author Jan Schönfeld
 */
public class SignUpActivity extends AppCompatActivity {

    public final String TAG = "SignUpActivity";

    @Bind(R.id.edit_username)
    EditText usernameText;
    @Bind(R.id.edit_email)
    EditText emailText;
    @Bind(R.id.edit_street)
    EditText streetText;
    @Bind(R.id.edit_housenumber)
    EditText houseNrText;
    @Bind(R.id.edit_zipcode)
    EditText zipcodeText;
    @Bind(R.id.edit_city)
    EditText cityText;
    @Bind(R.id.edit_password)
    EditText passwordText;
    @Bind(R.id.edit_repeat_password)
    EditText passwordRepeatText;
    @Bind(R.id.edit_additional_info)
    EditText additionalInfoText;
    @Bind(R.id.btn_sign_up)
    Button signUpButton;
    @Bind(R.id.link_login)
    TextView loginLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
                if (v != null) {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    /**
     * Method to return to the MainActivity after the registration button is clicked.
     * Return toasts and do not redirect back if any of the textfields are empty or if the
     * passwords don't match.
     */
    public void signUp() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IApi api = retrofit.create(IApi.class);

        signUpButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Registriere...");
        progressDialog.show();

        String addInfo = null;
        if (!(additionalInfoText.getText().toString().isEmpty())) {
            addInfo = additionalInfoText.getText().toString();
        }

        SignUpRequest request = new SignUpRequest();
        request.setUsername(usernameText.getText().toString());
        request.setEmail(emailText.getText().toString());
        request.setStreet(streetText.getText().toString());
        request.setHouseNumber(houseNrText.getText().toString());
        request.setZipCode(zipcodeText.getText().toString());
        request.setCity(cityText.getText().toString());
        request.setPassword(passwordText.getText().toString());
        request.setAdditionalInfo(addInfo);
        Log.d(TAG, "Request Objekt erstellt");
        Call<ResponseBody> response = api.signUp(request);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    onSignupSuccess();

                } else {
                    progressDialog.dismiss();
                    onSignupFailed();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                signUpButton.setEnabled(true);
                Toast.makeText(getBaseContext(), "Verbindung zum Server fehlgeschlagen", Toast.LENGTH_LONG).show();
            }
        });


    }

    /**
     * Contains what happens on a succesful sign-up
     */
    public void onSignupSuccess() {
        Log.d(TAG, "erfolgreicher servercall");
        signUpButton.setEnabled(true);
        Toast.makeText(getBaseContext(), "Erfolgreiche Registrierung", Toast.LENGTH_LONG).show();
        setResult(RESULT_OK, null);
        finish();
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    /**
     * Contains what happens on a failed sign-up
     */
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Registrierung fehlgeschlagen", Toast.LENGTH_LONG).show();
        signUpButton.setEnabled(true);
    }

    /**
     * Method to validate the user input contained in the EditText fields
     *
     * @return True if the user inputs valid data into the EditText fields
     */
    public boolean validate() {
        boolean valid = true;

        String username = usernameText.getText().toString();
        String email = emailText.getText().toString();
        String street = streetText.getText().toString();
        String houseNr = houseNrText.getText().toString();
        String zipcode = zipcodeText.getText().toString();
        String city = cityText.getText().toString();
        String pw = passwordText.getText().toString();
        String repeatPw = passwordRepeatText.getText().toString();
        String addInfo = null;
        if (!(additionalInfoText.getText().toString().isEmpty())) {
            addInfo = additionalInfoText.getText().toString();
        }


        if (username.isEmpty() || username.length() < 3) {
            usernameText.setError("mindestens 3 buchstaben");
            valid = false;
        } else {
            usernameText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("gib eine gültige email-addresse ein");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (street.isEmpty()) {
            streetText.setError("keine straße angegeben");
            valid = false;
        } else {
            streetText.setError(null);
        }

        if (houseNr.isEmpty()) {
            houseNrText.setError("keine hausnummer angegeben");
            valid = false;
        } else {
            houseNrText.setError(null);
        }

        if (zipcode.isEmpty()) {
            zipcodeText.setError("keine postleitzahl angegeben");
            valid = false;
        } else {
            zipcodeText.setError(null);
        }

        if (city.isEmpty()) {
            cityText.setError("keine stadt angegeben");
            valid = false;
        } else {
            cityText.setError(null);
        }

        //TODO password requirements for signup
        if (pw.isEmpty()) {
            passwordText.setError("kein passwort angegeben");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (repeatPw.isEmpty() || !(repeatPw.equals(pw))) {
            passwordRepeatText.setError("passwörter stimmen nicht überein");
            valid = false;
        } else {
            passwordRepeatText.setError(null);
        }

        if (addInfo != null) {
            if (addInfo.length() > 50) {
                passwordText.setError("maximal 50 zeichen");
                valid = false;
            } else {
                passwordText.setError(null);
            }
        }


        return valid;
    }
}



