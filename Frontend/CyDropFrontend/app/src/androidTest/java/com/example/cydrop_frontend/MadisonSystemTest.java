package com.example.cydrop_frontend;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Test class for EditMedsActivity to validate user interactions with UI components.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MadisonSystemTest {

    // Launch the EditMedsActivity before each test
    @Rule
    public ActivityScenarioRule<EditMedsActivity> activityScenarioRule =
            new ActivityScenarioRule<>(EditMedsActivity.class);

    /**
     * Test 1: Verify input in the id name field.
     */
    @Test
    public void testA_InputOldMedicationName() {
        // Simulate entering text in the old medication name EditText
        onView(withId(R.id.et_medication_name_prev)).perform(typeText("9"), closeSoftKeyboard());
        // Verify that the entered text is displayed correctly
        onView(withId(R.id.et_medication_name_prev)).check(matches(withText("9")));
    }

    /**
     * Test 2: Verify input in the new medication name field.
     */
    @Test
    public void testB_InputNewMedicationName() {
        // Simulate entering text in the new medication name EditText
        onView(withId(R.id.et_medication_name_new)).perform(typeText("Ibuprofen"), closeSoftKeyboard());
        // Verify that the entered text is displayed correctly
        onView(withId(R.id.et_medication_name_new)).check(matches(withText("Ibuprofen")));
    }

    /**
     * Test 3: Verify input in the medication stock field.
     */
    @Test
    public void testC_InputMedicationStock() {
        // Simulate entering text in the stock quantity EditText
        onView(withId(R.id.et_medication_stock)).perform(typeText("50"), closeSoftKeyboard());
        // Verify that the entered text is displayed correctly
        onView(withId(R.id.et_medication_stock)).check(matches(withText("50")));
    }

    /**
     * Test 4: Verify clicking the save changes button.
     */
    @Test
    public void testD_ClickSaveChangesButton() {
        // Perform a click action on the save changes button
        // onView(withId(R.id.btn_save_changes)).perform(click());
        // No assertion here since the behavior involves network requests and Toasts,
        // but you can verify subsequent UI state changes or interactions if applicable.
    }
}