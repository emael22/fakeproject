package com.djit.apps.fakeproject;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ControlledActivityTestRule<MainActivity> mActivityTestRule = new ControlledActivityTestRule<>(MainActivity.class);


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
    public void turnOnUpdateBulbState() {
        Spoon.screenshot(mActivityTestRule.getActivity(), "before-turn-on");
        MainScenario.turnBulbOn();
        onView(withId(R.id.activity_main_tv_bulb_state)).check(matches(withText(R.string.bulb_on)));
        Spoon.screenshot(mActivityTestRule.getActivity(), "after-turn-on");
    }

    @Test
    public void turnOnUpdateBtnTurnOnState() {
        Spoon.screenshot(mActivityTestRule.getActivity(), "before-turn-on");
        MainScenario.turnBulbOn();
        onView(withId(R.id.main_activity_btn_turn_on)).check(matches(withText(R.string.change_color)));
        Spoon.screenshot(mActivityTestRule.getActivity(), "after-turn-on");
    }

    @Test
    public void turnOff() {
        Spoon.screenshot(mActivityTestRule.getActivity(), "before-turn-off");
        MainScenario.turnBulbOff();
        Spoon.screenshot(mActivityTestRule.getActivity(), "after-turn-off");
    }

    @Test
    public void turnOffSetBulbState() {
        Spoon.screenshot(mActivityTestRule.getActivity(), "before-turn-on");
        MainScenario.turnBulbOff();
        onView(withId(R.id.activity_main_tv_bulb_state)).check(matches(withText(R.string.bulb_off)));
        Spoon.screenshot(mActivityTestRule.getActivity(), "before-turn-on");
    }

    @Test
    public void turnOffSetBulbColor() {
        Spoon.screenshot(mActivityTestRule.getActivity(), "before-turn-on");
        MainScenario.turnBulbOff();
        String defaultHexString = Integer.toHexString(ColoredLightBulb.DEFAULT_COLOR);
        onView(withId(R.id.activity_main_tv_bulb_color)).check(matches(withText(defaultHexString)));
        Spoon.screenshot(mActivityTestRule.getActivity(), "after-turn-off");
    }

    @Test
    public void btnTurnOffDisabled() {
        Spoon.screenshot(mActivityTestRule.getActivity(), "before-turn-on");
        onView(withId(R.id.main_activity_btn_turn_off)).check(matches(not(isEnabled())));
    }

    @Test
    public void btnTurnOnEnabled() {
        Spoon.screenshot(mActivityTestRule.getActivity(), "before-turn-on");
        onView(withId(R.id.main_activity_btn_turn_on)).perform(click());
        onView(withId(R.id.main_activity_btn_turn_on)).check(matches(isEnabled()));
        Spoon.screenshot(mActivityTestRule.getActivity(), "after-turn-on");
    }

    @Test
    public void btnVoltageUpEnabled() {
        onView(withId(R.id.main_activity_btn_voltage_up)).perform(click());
        onView(withId(R.id.main_activity_btn_voltage_up)).check(matches(isEnabled()));
    }

    @Test
    public void btnVoltageDownDisabled() {
        onView(withId(R.id.main_activity_btn_voltage_down)).check(matches(not(isEnabled())));
    }

    @Test
    public void defaultVoltageView() {
        String defaultVoltage = Integer.toString(EnergyPlant.MIN_VOLTAGE);
        onView(withId(R.id.activity_main_tv_bulb_voltage)).check(matches(withText(defaultVoltage)));
    }

    @Test
    public void newVoltageViewUp() {
        String newVoltageUp = Integer.toString(EnergyPlant.MIN_VOLTAGE + 1);
        onView(withId(R.id.main_activity_btn_voltage_up)).perform(click());
        onView(withId(R.id.activity_main_tv_bulb_voltage)).check(matches(withText(newVoltageUp)));
    }

    @Test
    public void disableVoltageUp() {
        Spoon.screenshot(mActivityTestRule.getActivity(), "before-looping");
        int loopValue = EnergyPlant.MAX_VOLTAGE - EnergyPlant.MIN_VOLTAGE;
        for (int i = 0; i < loopValue; i++) {
            onView(withId(R.id.main_activity_btn_voltage_up)).perform(click());
        }
        onView(withId(R.id.main_activity_btn_voltage_up)).check(matches(not(isEnabled())));
        Spoon.screenshot(mActivityTestRule.getActivity(), "after-looping");
    }

    @Test
    public void enableVoltageDown() {
        onView(withId(R.id.main_activity_btn_voltage_up)).perform(click());
        onView(withId(R.id.main_activity_btn_voltage_down)).check(matches(isEnabled()));
    }

    @Test
    public void newVoltageViewDown() {
        String defaultVoltage = Integer.toString(EnergyPlant.MIN_VOLTAGE);
        onView(withId(R.id.main_activity_btn_voltage_up)).perform(click());
        onView(withId(R.id.main_activity_btn_voltage_down)).perform(click());
        onView(withId(R.id.activity_main_tv_bulb_voltage)).check(matches(withText(defaultVoltage)));
    }

}
