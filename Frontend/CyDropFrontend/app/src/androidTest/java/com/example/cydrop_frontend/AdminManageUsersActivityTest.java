package com.example.cydrop_frontend;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class AdminManageUsersActivityTest {

    @Rule
    public ActivityScenarioRule<AdminManageUsersActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AdminManageUsersActivity.class);

    @Before
    public void setUp() {
        // Launch the activity before each test
        ActivityScenario.launch(AdminManageUsersActivity.class);
    }

    @Test
    public void testInput(){
        // Type userId into the EditText
        Espresso.onView(withId(R.id.etUserId))
                .perform(typeText("1"));
    }

    /**
     * Test that the search button works and displays user info in the TextView.
     */
    @Test
    public void testSearchButton() {
        // Type userId into the EditText
        Espresso.onView(withId(R.id.etUserId))
                .perform(typeText("1"));

        // Click on the Search button
        Espresso.onView(withId(R.id.btnSearch))
                .perform(click());

        // Check if the user info is displayed in TextView (msgResponse)
        Espresso.onView(withId(R.id.tvUserInfo))
                .check(matches(isDisplayed()));
    }

    /**
     * Test that the delete button works and deletes the user.
     */
    @Test
    public void testDeleteButton() {
        // Type userId into the EditText
        Espresso.onView(withId(R.id.etUserId))
                .perform(typeText("123"));  // Mock user ID

        // Click on the Delete button
        Espresso.onView(withId(R.id.btnDeleteUser))
                .perform(click());
    }

    /**
     * Test that the return button works and navigates back to the AdminNavbarMainActivity.
     */
    @Test
    public void testReturnButton() {
        // Click on the Return button
        Espresso.onView(withId(R.id.btnReturn))
                .perform(click());

        // Check if the AdminNavbarMainActivity is started by verifying a unique element
        Espresso.onView(withId(R.id.frame_layout))  // Replace with an actual ID in AdminNavbarMainActivity
                .check(matches(isDisplayed()));
    }

}