package fhm.wi.team5.android_application;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.text.WordUtils;

import java.io.IOException;
import java.util.logging.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import fhm.wi.team5.android_application.model.User;
import fhm.wi.team5.android_application.service.IApi;
import fhm.wi.team5.android_application.service.SharedPreferencesService;
import fhm.wi.team5.android_application.transfer.LoginRequest;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static fhm.wi.team5.android_application.service.BaseUrl.BASE_URL;

/**
 * Activity that offers a login formular and links to SignUpActivity/ForgotPasswordActivity.
 * This activity gets skipped if theres non-default value in token, userId and hasLoggedIn.
 *
 * @author Jan Schönfeld
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final int REQUEST_FORGOTPW = 0;
    User user;
    Logger logger;
    @Bind(R.id.login_email)
    EditText emailText;
    @Bind(R.id.login_password)
    EditText passwordText;
    @Bind(R.id.btn_login)
    Button loginButton;
    @Bind(R.id.link_signup)
    TextView signupLink;
    @Bind(R.id.link_forgot_pw)
    TextView forgotPwLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        boolean loginStatus = SharedPreferencesService.retrieveBooleanFromSharedPreference("hasLoggedIn", getBaseContext());
        long userId = SharedPreferencesService.retrieveLongFromSharedPreference("userId", getBaseContext());
        if (loginStatus && (userId != 0)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
                if (v != null) {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        forgotPwLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivityForResult(intent, REQUEST_FORGOTPW);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

    }

    /**
     * Method to login a user into our server using Retrofit2.
     * Writes String token, boolean "hasLoggedIn" and long "userId" to SharedPreferences.
     */
    public void login() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IApi api = retrofit.create(IApi.class);

        Log.d(TAG, "Login");

        if (!validate()) {
            loginButton.setEnabled(true);
            return;
        }

        loginButton.setEnabled(false);


        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        LoginRequest request = new LoginRequest(email, password);
        Call<ResponseBody> call = api.login(request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    Log.d("THE: ", response.headers().toString());
                    Headers headers = response.headers();
                    String token = headers.get("authorization");


                    SharedPreferencesService.addToSharedPreference("Authorization", token, getBaseContext());
                    SharedPreferencesService.addToSharedPreference("hasLoggedIn", true, getBaseContext());
                    try {
                        String serverResponse = response.body().string();
                        Log.d("WATCH THIS: ", serverResponse);
                        Gson gson = new GsonBuilder().create();
                        user = gson.fromJson(serverResponse, User.class);
                        if (user != null) {
                            SharedPreferencesService.addToSharedPreference("userId", user.getId(), getBaseContext());
                            Log.d(TAG, "Benutzername: " + user.getUsername());
                            progressDialog.cancel();
                            onLoginSuccess(user.getUsername());

                        } else {
                            progressDialog.dismiss();
                            onLoginFailed();
                        }

                    } catch (IOException ex) {
                        logger.info("IOException");
                    }

                } else {
                    progressDialog.dismiss();
                    onLoginFailed();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                loginButton.setEnabled(true);
                Toast.makeText(getBaseContext(), "Verbindung zum Server fehlgeschlagen", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    /**
     * Contains what happens on a succesful login
     */
    public void onLoginSuccess(String userName) {
        loginButton.setEnabled(true);
        Log.d(TAG, "Erfolgreicher Login");
        Toast.makeText(getBaseContext(), "Herzlich Willkommen, " + WordUtils.capitalize(userName.replace(".", " ")), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    /**
     * Contains what happens on a failed login
     */
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login fehlgeschlagen", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }


    /**
     * Method to validate the user input contained in the EditText fields
     *
     * @return True if the user inputs valid data into the EditText fields
     */
    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("bitte gib eine gültige email addresse ein");
            valid = false;
        } else {
            emailText.setError(null);
        }
        //TODO implement password requirements
        if (password.isEmpty()) {
            passwordText.setError("bitte gib ein gültiges passwort ein");
        } else {
            passwordText.setError(null);
        }
        return valid;
    }

}


