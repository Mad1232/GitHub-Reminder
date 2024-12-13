package com.example.cydrop_frontend;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Gravity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ClientNavbarMainActivityTest {

    @Before
    public void setUp() {
        // Launch the activity before each test
        ActivityScenario.launch(ClientNavbarMainActivity.class);
    }

    @Test
    public void TestClientTabs(){
        // Confirm pet tab works
        onView(withId(R.id.customers)).perform(click());
        onView(withId(R.id.addInventoryTextView2)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Confirm Reminders tab works
        onView(withId(R.id.reminders)).perform(click());
        onView(withId(R.id.client_reminders_text_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Confirm questions tab works
        onView(withId(R.id.questions)).perform(click());
        onView(withId(R.id.global_chat_text_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Logout
        onView(withId(R.id.customers)).perform(click());
        onView(withId(R.id.client_logout_button)).perform(click());
    }

}
