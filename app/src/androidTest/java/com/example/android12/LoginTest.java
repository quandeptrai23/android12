package com.example.android12;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityScenarioRule<Login> activityRule = new ActivityScenarioRule<>(Login.class);
    @Before
    public void setUp() {
        // Khởi tạo Intents trước khi thực hiện test
        Intents.init();
        Context context = ApplicationProvider.getApplicationContext();
        TestDBHelper dbHelper = new TestDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
    }

    @After
    public void tearDown() {
        // Giải phóng Intents sau khi hoàn thành test
        Intents.release();
    }
    @Test
    public void testLoginSuccess() {
        // Nhập thông tin đăng nhập đúng
        onView(withId(R.id.editUsername)).perform(typeText("naq"), closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(typeText("123"), closeSoftKeyboard());

        // Nhấn nút Đăng nhập
        onView(withId(R.id.btnLogin)).perform(click());

        // Kiểm tra xem đã chuyển sang MainActivity chưa
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void testLoginFailure() {
        // Nhập thông tin đăng nhập sai
        onView(withId(R.id.editUsername)).perform(typeText("wronguser"), closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(typeText("wrongpass"), closeSoftKeyboard());

        // Nhấn nút Đăng nhập
        onView(withId(R.id.btnLogin)).perform(click());

        // Kiểm tra sự xuất hiện của Toast
        onView(withText("Tài khoản hoặc mật khẩu không chính xác!"))
                .inRoot(new ToastMatcher())
                .check(matches(withText("Tài khoản hoặc mật khẩu không chính xác!")));
    }

    @Test
    public void testEmptyFields() {
        // Để trống các trường và nhấn nút Đăng nhập
        onView(withId(R.id.btnLogin)).perform(click());

        // Kiểm tra thông báo lỗi yêu cầu nhập đầy đủ thông tin
        onView(withText("Yêu cầu nhập đầy đủ thông tin"))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
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
                .check(matches(isDisplayed()));
    }
}

