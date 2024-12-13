package com.example.cydrop_frontend;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.hamcrest.Matcher;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class VetInfoActivityTest {
    @Rule
    public ActivityScenarioRule<VetInfoActivity> activityRule = new ActivityScenarioRule<>(VetInfoActivity.class);

    /**
     * This method runs before each test case.
     */
    @Before
    public void setUp() {
        // Launch the activity
        ActivityScenario.launch(VetInfoActivity.class);
    }

    /**
     * Test if the **VetInfoActivity** loads successfully.
     */
    @Test
    public void testActivityLaunch() {
        onView(withId(R.id.data_text)) // Check if the TextView with id "data_text" is displayed
                .check(matches(isDisplayed()));
    }

    /**
     * Test that the **Return Button** navigates to the next activity.
     */
    @Test
    public void testReturnButtonClick() {
        onView(withId(R.id.returnButton)) // Check if the Return Button is displayed
                .check(matches(isDisplayed()))
                .perform(click()); // Click the Return Button

        // Check if the new activity is displayed (assuming R.id.some_unique_id exists in ClientNavbarMainActivity)
        onView(withId(R.id.frame_layout)) // Replace with the layout id of the next activity
                .check(matches(isDisplayed()));
    }

    /**
     * Test that the **Chat Button** launches the Chat Activity.
     */
//    @Test
//    public void testChatButtonClick() {
//        onView(withId(R.id.chat_btn)) // Check if the Chat Button is displayed
//                .check(matches(isDisplayed()))
//                .perform(click()); // Click the Chat Button
//
//        onView(withId(R.id.vet_questions_linear_layout)) // Replace with the layout id of the next activity
//                .check(matches(isDisplayed()));
//    }
}
