package com.djit.apps.fakeproject;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.ControlledActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.test.suitebuilder.annotation.LargeTest;

import com.squareup.spoon.Spoon;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityRestoreTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) {

        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
            // Fake a state
            Context targetContext = InstrumentationRegistry.getTargetContext();
            SharedPreferences sharedPref = targetContext.getSharedPreferences("light_bulb_state", Context.MODE_PRIVATE);

            sharedPref.edit()
                    .putInt("color_pref", Color.BLUE)
                    .putBoolean("bulb_state_on", true)
                    .apply();
        }

    };

    @Before
    public void before() {
        clearSharedPreferences();
    }

    @After
    public void after() {
        clearSharedPreferences();
    }

    private void clearSharedPreferences() {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        SharedPreferences sharedPref = targetContext.getSharedPreferences("light_bulb_state", Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
    }

    @Test
    public void restoreStateFromSharedPreferences() {
        Spoon.screenshot(mActivityTestRule.getActivity(), "restored-blue-and-on");

        onView(withId(R.id.activity_main_tv_bulb_state)).check(matches(withText(R.string.bulb_on)));

        String defaultHexString = Integer.toHexString(Color.BLUE);
        onView(withId(R.id.activity_main_tv_bulb_color)).check(matches(withText(defaultHexString)));
    }


}
