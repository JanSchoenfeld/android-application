package fhm.wi.team5.android_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static fhm.wi.team5.android_application.service.EmailValidator.isValidEmail;

/**
 * @author Lucas Nebot
 * @author Jan Schönfeld
 *         Simple Activity to recover a lost password.
 */
public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private final String TAG = ForgotPasswordActivity.this.getClass().getSimpleName();

    private Button mBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        EditText mInput = (EditText) findViewById(R.id.edit_email);
        mBtn = (Button) findViewById(R.id.button_action);

        mBtn.setVisibility(View.GONE);

        mInput.addTextChangedListener(this);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (isValidEmail(s)) {
            Log.d(TAG, "Text Input is a valid email");
            mBtn.setVisibility(View.VISIBLE);
        } else {
            mBtn.setVisibility(View.GONE);
        }
    }

    /**
     * Implements the reaction of view elements to click events
     *
     * @param v the clicked View element
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == mBtn.getId()) {
            Log.d(TAG, "Send button was clicked");
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
    }

}

