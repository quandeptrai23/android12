package com.example.android12;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TimetableTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);
    @Before
    public void setUp() {
        // Khởi tạo Intents trước khi thực hiện test
        Intents.init();
    }

    @After
    public void tearDown() {
        // Giải phóng Intents sau khi hoàn thành test
        Intents.release();
    }
    @Test
    public void testTimetableNavigation() {
        // Input the correct username and password, then click login
        onView(withId(R.id.editUsername)).perform(typeText("naq"));
        onView(withId(R.id.editPassword)).perform(typeText("123"));
        onView(withId(R.id.btnLogin)).perform(click());

        // Click on the timetable icon
        onView(withId(R.id.imageTimetable)).perform(click());

        // Check if the timetable activity is displayed
        onView(withId(R.id.recyclerview))  // Check for RecyclerView from TimetableActivity
                .check(matches(isDisplayed()));    }
}
