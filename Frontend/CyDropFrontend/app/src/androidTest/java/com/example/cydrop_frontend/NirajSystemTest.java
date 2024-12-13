//package com.example.cydrop_frontend;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
//import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.preference.PreferenceManager;
//import android.view.Gravity;
//
//import androidx.test.core.app.ActivityScenario;
//import androidx.test.espresso.ViewAssertion;
//import androidx.test.espresso.ViewInteraction;
//import androidx.test.espresso.assertion.ViewAssertions;
//import androidx.test.espresso.contrib.DrawerActions;
//import androidx.test.espresso.matcher.ViewMatchers;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.platform.app.InstrumentationRegistry;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static org.junit.Assert.*;
//
//@RunWith(AndroidJUnit4.class)
//public class NirajSystemTest {
//
//    @Rule
//    public ActivityScenarioRule<MainActivity> activityScenarioRule
//            = new ActivityScenarioRule<>(MainActivity.class);
//
//    @Test
//    public void testFragmentSwitching() {
//        // Login
//        loginClient();
//
//        // Confirm pet tab works
//        onView(withId(R.id.customers)).perform(click());
//      //  onView(withId(R.id.customer_text)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
//
//        // Confirm Reminders tab works
//        onView(withId(R.id.reminders)).perform(click());
//        onView(withId(R.id.client_reminders_text_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
//
//        // Confirm questions tab works
//        onView(withId(R.id.questions)).perform(click());
//        onView(withId(R.id.global_chat_text_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
//
//        // Logout
//        onView(withId(R.id.customers)).perform(click());
//        onView(withId(R.id.client_logout_button)).perform(click());
//    }
//
//    @Test
//    public void testExpandingAndCollapsingPets(){
//        // Login
//        loginClient();
//
//        // Confirm we can reach pet tab
//        onView(withId(R.id.customers)).perform(click());
//     //   onView(withId(R.id.customer_text)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
//
//        onView(withId(R.id.card_pet_edit_button)).perform(click());
//        onView(withId(R.id.card_pet_edit_cancel_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
//
//        onView(withId(R.id.card_pet_edit_cancel_button)).perform(click());
//        onView(withId(R.id.card_pet_edit_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
//
//        // Logout
//        onView(withId(R.id.client_logout_button)).perform(click());
//    }
//
//    @Test
//    public void testStayLoggedIn(){
//
//        loginClient();
//
//        // Restart application
//        activityScenarioRule.getScenario().close();
//        ActivityScenario.launch(MainActivity.class, null);
//
//        // Look for logged in status
//       // onView(withId(R.id.customer_text)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
//
//        // Logout
//        onView(withId(R.id.client_logout_button)).perform(click());
//    }
//
//    @Test
//    public void testSwitchingUsers(){
//        // Login as client
//        loginClient();
//
//        // Confirm we can reach pet tab
//        onView(withId(R.id.customers)).perform(click());
//      //  onView(withId(R.id.customer_text)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
//
//        // Logout
//        onView(withId(R.id.client_logout_button)).perform(click());
//
//
//        // Login as admin
//        loginAdmin();
//        onView(withId(R.id.add_inventory_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
//
//        // Logout
//        onView(withId(R.id.adminLogoutButton)).perform(click());
//
//        // Login as vet
//        loginVet();
//        onView(withId(R.id.vet_customers_text)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
//
//        // Logout
//        onView(withId(R.id.vet_customers_logout_button)).perform(click());
//
//    }
//
//
//    private void loginClient(){
//        loginUser(
//                "madison@gmail.com",
//                "madison"
//        );
//    }
//
//    private void loginAdmin(){
//        loginUser(
//                "admin@gmail.com",
//                "admin"
//        );
//    }
//
//    private void loginVet(){
//        loginUser(
//                "mango@gmail.com",
//                "mango"
//        );
//    }
//
//
//    private void loginUser(String username, String password){
//        onView(withId(R.id.main_login_button)).perform(click());
//        onView(withId(R.id.login_username_edt)).perform(click()).perform(typeText(username));
//        onView(withId(R.id.login_password_edt)).perform(click()).perform(typeText(password));
//        onView(withId(R.id.login_login_btn)).perform(click());
//    }
//
//
//
//
//}
