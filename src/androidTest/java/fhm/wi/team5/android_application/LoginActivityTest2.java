package fhm.wi.team5.android_application;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fhm.wi.team5.android_application.service.SharedPreferencesService;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertFalse;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest2 {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginActivityTest2() {
        ViewInteraction appCompatEditText = onView(
                withId(R.id.login_email));
        appCompatEditText.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.login_email));
        appCompatEditText2.perform(scrollTo(), replaceText("1@1.de"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                withId(R.id.login_password));
        appCompatEditText3.perform(scrollTo(), replaceText("123"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_login), withText("Login")));
        appCompatButton.perform(scrollTo(), click());

        assertFalse (SharedPreferencesService.retrieveBooleanFromSharedPreference("hasLoggedIn", mActivityTestRule.getActivity()));

    }

    @Test
    public void loginActivityTest3() {
        ViewInteraction appCompatEditText = onView(
                withId(R.id.login_email));
        appCompatEditText.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.login_email));
        appCompatEditText2.perform(scrollTo(), replaceText("1@1.de"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_login), withText("Login")));
        appCompatButton.perform(scrollTo(), click());

        assertFalse (SharedPreferencesService.retrieveBooleanFromSharedPreference("hasLoggedIn", mActivityTestRule.getActivity()));

    }

}
