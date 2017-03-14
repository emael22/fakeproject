package com.djit.apps.fakeproject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Utility methods for dealing with the main screen.
 */
public final class MainScenario {

    public static void turnBulbOff() {
        onView(withId(R.id.main_activity_btn_turn_on)).perform(click());
        onView(withId(R.id.main_activity_btn_turn_off)).perform(click());
    }

    public static void turnBulbOn() {
        onView(withId(R.id.main_activity_btn_turn_on)).perform(click());
    }

    private MainScenario() {
        //no instance
    }
}
