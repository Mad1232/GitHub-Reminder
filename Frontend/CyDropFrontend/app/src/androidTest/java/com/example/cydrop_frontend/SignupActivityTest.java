package com.example.cydrop_frontend;

import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;


@RunWith(AndroidJUnit4.class)
public class SignupActivityTest {

    @Before
    public void setUp() {
        // Launch the SignupActivity
        androidx.test.core.app.ActivityScenario.launch(SignupActivity.class);
    }

    @Test
    public void testDisplayed() {
        Espresso.onView(withId(R.id.signup_username_edt))
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(withId(R.id.signup_password_edt))
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(withId(R.id.signup_signup_btn))
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(withId(R.id.signup_login_btn))
                .check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSignupButtonPress() {
        // Simulate entering a username (email) and password
        Espresso.onView(withId(R.id.signup_username_edt))
                .perform(typeText("testuser@example.com"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_password_edt))
                .perform(typeText("password123"), closeSoftKeyboard());

        // Press the signup button
        Espresso.onView(withId(R.id.signup_signup_btn))
                .perform(click());

    }

    @Test
    public void testLoginButtonPress() {
        // Press the login button to navigate to the LoginActivity
        Espresso.onView(withId(R.id.signup_login_btn))
                .perform(click());

        // Verify that the app navigates to the LoginActivity
        Espresso.onView(withId(R.id.login_username_edt))
                .check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testUsernameInput() {
        // Type a username (email) and check if it's correctly inputted
        Espresso.onView(withId(R.id.signup_username_edt))
                .perform(typeText("testuser@example.com"), closeSoftKeyboard());

        Espresso.onView(withId(R.id.signup_username_edt))
                .check(matches(withText("testuser@example.com")));
    }

    @Test
    public void testPasswordInput() {
        // Type a password and check if it's correctly inputted
        Espresso.onView(withId(R.id.signup_password_edt))
                .perform(typeText("password123"), closeSoftKeyboard());

        Espresso.onView(withId(R.id.signup_password_edt))
                .check(matches(withText("password123")));
    }
}