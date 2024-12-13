package com.example.cydrop_frontend;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class VetChatActivityTest {

    /**
     * This method runs before each test case to set up the scenario.
     */
    @Before
    public void setUp() {
        // Launch the VetChatActivity
        ActivityScenario.launch(VetChatActivity.class);
    }

    /**
     * Test to check if the activity launches and UI elements are displayed.
     */
    @Test
    public void testActivityLaunch() {
        // Check if the send button is visible
        onView(withId(R.id.sendBtn)).check(matches(isDisplayed()));

        // Check if the message input field is visible
        onView(withId(R.id.msgEdt)).check(matches(isDisplayed()));

        // Check if the return button is visible
        onView(withId(R.id.btn_return)).check(matches(isDisplayed()));
    }

    @Test
    public void testInput(){
        // Type userId into the EditText
        Espresso.onView(withId(R.id.msgEdt))
                .perform(typeText("Hello, World"));
    }

    /**
     * Test to check if clicking the Return button navigates back to ClientNavbarMainActivity.
     */
    @Test
    public void testReturnButtonClick() {
        // Click on the return button
        onView(withId(R.id.btn_return)).perform(click());

        // Check if the VetNavbarMainActivity is displayed (assuming a unique ID exists in the next activity)
        onView(withId(R.id.frame_layout)).check(matches(isDisplayed())); // Replace with an ID from ClientNavbarMainActivity
    }

    @Test
    public void testSendButtonClick() {
        // Type userId into the EditText
        Espresso.onView(withId(R.id.msgEdt))
                .perform(typeText("Hello, World"));

        // Click on the send button
        onView(withId(R.id.sendBtn)).perform(click());
    }
}