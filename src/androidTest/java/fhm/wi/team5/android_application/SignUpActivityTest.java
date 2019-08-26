package fhm.wi.team5.android_application;


import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import fhm.wi.team5.android_application.service.SharedPreferencesService;

import static android.os.Build.VERSION_CODES.N;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * @author Jan Schönfeld
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {

    String user;
    Random random;

    public static String generateString(Random rng, String characters, int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    @Before
    public void setUp(){
        random = new Random();
         user = generateString(random, "abcdefg12345", 5);
    }


    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void signUpActivityTest() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.link_signup), withText("Noch kein Account? Erstelle einen")));
        appCompatTextView.perform(scrollTo(), click());

        ViewInteraction appCompatEditText = onView(
                withId(R.id.edit_username));
        appCompatEditText.perform(scrollTo(), replaceText(user), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.edit_email));
        appCompatEditText2.perform(scrollTo(), replaceText(user + "@mail.de"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                withId(R.id.edit_street));
        appCompatEditText3.perform(scrollTo(), replaceText("Test"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                withId(R.id.edit_housenumber));
        appCompatEditText4.perform(scrollTo(), replaceText("31"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                withId(R.id.edit_zipcode));
        appCompatEditText5.perform(scrollTo(), replaceText("44123"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                withId(R.id.edit_city));
        appCompatEditText6.perform(scrollTo(), replaceText("Dorttmund"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                withId(R.id.edit_password));
        appCompatEditText7.perform(scrollTo(), replaceText("1234"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                withId(R.id.edit_repeat_password));
        appCompatEditText8.perform(scrollTo(), replaceText("1234"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                withId(R.id.edit_additional_info));
        appCompatEditText9.perform(scrollTo(), replaceText("Keine"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_sign_up), withText("Registrieren")));
        appCompatButton.perform(scrollTo(), click());

        SystemClock.sleep(1000);

        ViewInteraction appCompatEditText10 = onView(
                withId(R.id.login_email));
        appCompatEditText10.perform(scrollTo(), click());

        ViewInteraction appCompatEditText11 = onView(
                withId(R.id.login_email));
        appCompatEditText11.perform(scrollTo(), replaceText(user + "@mail.de"), closeSoftKeyboard());

        ViewInteraction appCompatEditText12 = onView(
                withId(R.id.login_password));
        appCompatEditText12.perform(scrollTo(), replaceText("1234"), closeSoftKeyboard());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.btn_login), withText("Login")));
        appCompatButton13.perform(scrollTo(), click());

        SystemClock.sleep(3000);
        assert (SharedPreferencesService.retrieveBooleanFromSharedPreference("hasLoggedIn", mActivityTestRule.getActivity()));


    }

    @After
    public void logout(){
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Öffne"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatCheckedTextView4 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Logout"), isDisplayed()));
        appCompatCheckedTextView4.perform(click());
    }

}
