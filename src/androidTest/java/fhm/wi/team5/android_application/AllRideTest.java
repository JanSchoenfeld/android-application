package fhm.wi.team5.android_application;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
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
public class AllRideTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void allRideTest() {
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

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Öffne"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Meine Leihgaben"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Öffne"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Meine Ausleihen"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1),
                        childAtPosition(
                                withId(R.id.rental_list),
                                0),
                        isDisplayed()));
        textView.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1),
                        childAtPosition(
                                withId(R.id.rental_property_list),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.bike_picture),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

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

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
