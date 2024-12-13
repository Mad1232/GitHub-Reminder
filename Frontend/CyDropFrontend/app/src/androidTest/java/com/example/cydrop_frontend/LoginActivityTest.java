package com.example.cydrop_frontend;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

public class LoginActivityTest {

        @Before
        public void setUp() {
            // Launch the LoginActivity
            androidx.test.core.app.ActivityScenario.launch(LoginActivity.class);
        }

            @Test
            public void testDisplayed() {
                Espresso.onView(withId(R.id.login_login_btn))
                        .check(matches(ViewMatchers.isDisplayed()));

                Espresso.onView(withId(R.id.login_signup_btn))
                        .check(matches(ViewMatchers.isDisplayed()));

                Espresso.onView(withId(R.id.login_username_edt))
                        .check(matches(ViewMatchers.isDisplayed()));

                Espresso.onView(withId(R.id.login_password_edt))
                        .check(matches(ViewMatchers.isDisplayed()));
            }

            @Test
            public void testEmailInput() {
                // Type in a sample email and check that the input field is updated
                Espresso.onView(withId(R.id.login_username_edt))
                        .perform(typeText("testuser@example.com"), closeSoftKeyboard());

                // Verify that the email input field contains the correct text
                Espresso.onView(withId(R.id.login_username_edt))
                        .check(matches(withText("testuser@example.com")));
            }

            @Test
            public void testPasswordInput() {
                // Type in a sample password and check that the input field is updated
                Espresso.onView(withId(R.id.login_password_edt))
                        .perform(typeText("password123"), closeSoftKeyboard());

                // Verify that the password input field contains the correct text
                Espresso.onView(withId(R.id.login_password_edt))
                        .check(matches(withText("password123")));
            }

        @Test
        public void testLoginButtonPress() {
            // Type in a sample email and password
            Espresso.onView(withId(R.id.login_username_edt))
                    .perform(typeText("madison@gmail.com"), closeSoftKeyboard());
            Espresso.onView(withId(R.id.login_password_edt))
                    .perform(typeText("madison"), closeSoftKeyboard());

            // Press the login button
            Espresso.onView(withId(R.id.login_login_btn))
                    .perform(click());
        }

        @Test
        public void testSignupButtonPress() {
            // Press the signup button
            Espresso.onView(withId(R.id.login_signup_btn))
                    .perform(click());

            Espresso.onView(withId(R.id.signup_username_edt))
                    .check(matches(ViewMatchers.isDisplayed()));
        }

  }
