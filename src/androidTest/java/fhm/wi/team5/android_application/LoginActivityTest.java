package fhm.wi.team5.android_application;


import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fhm.wi.team5.android_application.service.SharedPreferencesService;

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
import static org.junit.Assert.assertFalse;

/**
 * @author Jan Schönfeld
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginActivityTest() {
        ViewInteraction appCompatEditText = onView(
                withId(R.id.login_email));
        appCompatEditText.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.login_email));
        appCompatEditText2.perform(scrollTo(), replaceText("1@1.de"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                withId(R.id.login_password));
        appCompatEditText3.perform(scrollTo(), replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_login), withText("Login")));
        appCompatButton.perform(scrollTo(), click());

        SystemClock.sleep(1000);

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
