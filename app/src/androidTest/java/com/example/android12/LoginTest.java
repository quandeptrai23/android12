package com.example.android12;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import java.io.File;

import ReStoreData.DataRestoreHelper;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityScenarioRule<Login> activityScenarioRule =
            new ActivityScenarioRule<>(Login.class);

    @Test
    public void testLogin() {
        // Khởi tạo Espresso Intents
        Intents.init();

        // Nhập tên đăng nhập
        onView(withId(R.id.editUsername)).perform(typeText("hvs"));

        // Nhập mật khẩu
        onView(withId(R.id.editPassword)).perform(typeText("123"));

        // Nhấn nút "Đăng Nhập"
        onView(withId(R.id.btnLogin)).perform(click());

        // Kiểm tra xem MainActivity có được mở hay không
        intended(hasComponent(MainActivity.class.getName()));



        // Kết thúc Espresso Intents
        Intents.release();

    }
    @Test
    public void testMain(){
        //Nhấn Image Result
        onView(withId(R.id.imageResult)).perform(click());
    }
}
