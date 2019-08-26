package fhm.wi.team5.android_application;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.github.javafaker.Faker;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Lucas Nebot
 *         User interface test for ForgotPasswordActivity
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ForgotPasswordActivityUiTest {
    private final String TAG = ForgotPasswordActivityUiTest.this.getClass().getSimpleName();
    @Rule
    public ActivityTestRule<ForgotPasswordActivity> mActivityRule =
            new ActivityTestRule<>(ForgotPasswordActivity.class);
    private String mValidEmail;

    /**
     * Sets up necessary test resources
     */
    @Before
    public void setUp() {
        Faker mFaker = new Faker();
        mValidEmail = mFaker.internet().emailAddress();
        Log.d(TAG, "The faked email is: " + mValidEmail);

    }

    /**
     * Checks if randomly generated valid emails are recognized and the send-button appears.
     */
    @Test
    public void enterValidEmail() {
        // Type in valid email
        onView(withId(R.id.edit_email))
                .perform(typeText(mValidEmail), closeSoftKeyboard());
        //check tha the "Reset Password"-Button has appeared
        onView(withId(R.id.button_action))
                .check(matches(isDisplayed()));
        onView(withId(R.id.edit_email))
                .perform(clearText());

    }
}
