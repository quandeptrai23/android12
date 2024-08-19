package com.example.android12;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.Intent;
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

import HVHuy.Message;

@RunWith(AndroidJUnit4.class)
public class MessageTest {

    @Rule
    public ActivityScenarioRule<Login> activityRule = new ActivityScenarioRule<>(Login.class);

    @Before
    public void setUp() {
        // Khởi tạo Intents trước khi thực hiện test
        Intents.init();
        Context context = ApplicationProvider.getApplicationContext();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
    }

    @After
    public void tearDown() {
        // Giải phóng Intents sau khi hoàn thành test
        Intents.release();
    }

    @Test
    public void testMessageScreenInitialization() {
        // Nhập thông tin đăng nhập đúng
        onView(withId(R.id.editUsername)).perform(typeText("vmq"), closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(typeText("123"), closeSoftKeyboard());

        // Nhấn nút Đăng nhập
        onView(withId(R.id.btnLogin)).perform(click());

        // Tiếp tục nhấn vào biểu tượng cố vấn học tập
        onView(withId(R.id.imageMessage)).perform(click());

        // Nhấn nút liên hệ
        onView(withId(R.id.btn_contact_messageDetails)).perform(click());

        // Kiểm tra các thành phần trong màn hình Gửi Tin Nhắn có được khởi tạo đúng không
        onView(withId(R.id.txt_email_contact)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_title_contact)).check(matches(isDisplayed()));
        onView(withId(R.id.txt_description_contact)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_sendEmail_contact)).check(matches(isDisplayed()));
    }

    @Test
    public void testSendMessageSuccess() {
        // Nhập thông tin đăng nhập đúng
        onView(withId(R.id.editUsername)).perform(typeText("vmq"), closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(typeText("123"), closeSoftKeyboard());

        // Nhấn nút Đăng nhập
        onView(withId(R.id.btnLogin)).perform(click());

        // Tiếp tục nhấn vào biểu tượng cố vấn học tập
        onView(withId(R.id.imageMessage)).perform(click());

        // Nhấn nút liên hệ
        onView(withId(R.id.btn_contact_messageDetails)).perform(click());

        // Nhập tiêu đề và nội dung tin nhắn
        onView(withId(R.id.txt_title_contact)).perform(typeText("Test Title"), closeSoftKeyboard());
        onView(withId(R.id.txt_description_contact)).perform(typeText("Test Message"), closeSoftKeyboard());

        // Nhấn nút gửi email
        onView(withId(R.id.btn_sendEmail_contact)).perform(click());

        // Kiểm tra xem Intent gửi email có được khởi tạo đúng không
        intended(hasAction(Intent.ACTION_SENDTO));
        intended(hasExtra(Intent.EXTRA_SUBJECT, "Test Title"));
        intended(hasExtra(Intent.EXTRA_TEXT, "Test Message"));
    }

    @Test
    public void testSendMessageFailure() {
        // Nhập thông tin đăng nhập đúng
        onView(withId(R.id.editUsername)).perform(typeText("vmq"), closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(typeText("123"), closeSoftKeyboard());

        // Nhấn nút Đăng nhập
        onView(withId(R.id.btnLogin)).perform(click());

        // Tiếp tục nhấn vào biểu tượng cố vấn học tập
        onView(withId(R.id.imageMessage)).perform(click());

        // Nhấn nút liên hệ
        onView(withId(R.id.btn_contact_messageDetails)).perform(click());

        // Nhập tiêu đề và nội dung tin nhắn
        onView(withId(R.id.txt_title_contact)).perform(typeText("Test Title"), closeSoftKeyboard());
        onView(withId(R.id.txt_description_contact)).perform(typeText("Test Message"), closeSoftKeyboard());

        // Nhấn nút gửi email
        onView(withId(R.id.btn_sendEmail_contact)).perform(click());

        // Giả lập trường hợp không có ứng dụng email
        // Đoạn mã này sẽ khác tùy thuộc vào cách bạn muốn giả lập tình huống không có ứng dụng email

        // Kiểm tra xem thông báo lỗi có xuất hiện không
        onView(withText("Không có ứng dụng email nào được cài đặt.")).check(matches(isDisplayed()));
    }

    @Test
    public void testCancelMessageSending() {
        // Nhập thông tin đăng nhập đúng
        onView(withId(R.id.editUsername)).perform(typeText("vmq"), closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(typeText("123"), closeSoftKeyboard());

        // Nhấn nút Đăng nhập
        onView(withId(R.id.btnLogin)).perform(click());

        // Tiếp tục nhấn vào biểu tượng cố vấn học tập
        onView(withId(R.id.imageMessage)).perform(click());
//
        // Nhấn nút liên hệ
        onView(withId(R.id.btn_contact_messageDetails)).perform(click());

        // Nhấn nút Hủy (Cancel)
        onView(withId(R.id.cancelID)).perform(click());
        // Kiểm tra xem dialog có bị đóng không
        onView(withId(R.id.cancelID)).check(doesNotExist());
    }

}

