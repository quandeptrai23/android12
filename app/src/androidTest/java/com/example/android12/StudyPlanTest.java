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

import LEVANCUONG.StudyPlan;

@RunWith(AndroidJUnit4.class)
public class StudyPlanTest {

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
    public void testLoginSuccessAndNavigateToStudyPlan() {
        // Nhập thông tin đăng nhập đúng
        onView(withId(R.id.editUsername)).perform(typeText("vmq"), closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(typeText("123"), closeSoftKeyboard());

        // Nhấn nút Đăng nhập
        onView(withId(R.id.btnLogin)).perform(click());

        // Kiểm tra xem đã chuyển sang MainActivity chưa
        intended(hasComponent(MainActivity.class.getName()));

        // Tiếp tục nhấn vào biểu tượng kế hoạch học tập (StudyPlan)
        onView(withId(R.id.btnStudyplane)).perform(click());

        // Kiểm tra xem đã chuyển sang StudyPlanActivity chưa
        intended(hasComponent(StudyPlan.class.getName()));

        // Kiểm tra các thành phần trong StudyPlan có được hiển thị đúng không
        onView(withId(R.id.lv)).check(matches(isDisplayed()));  // Kiểm tra ListView
        onView(withId(R.id.sp)).check(matches(isDisplayed()));  // Kiểm tra Spinner
        onView(withId(R.id.txtUpdateDate)).check(matches(isDisplayed()));  // Kiểm tra TextView ngày cập nhật
    }

    @Test
    public void testWidgetsInitializationInStudyPlan() {
        // Nhập thông tin đăng nhập đúng
        onView(withId(R.id.editUsername)).perform(typeText("vmq"), closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(typeText("123"), closeSoftKeyboard());

        // Nhấn nút Đăng nhập
        onView(withId(R.id.btnLogin)).perform(click());

        // Tiếp tục nhấn vào biểu tượng kế hoạch học tập (StudyPlan)
        onView(withId(R.id.btnStudyplane)).perform(click());

        // Kiểm tra các thành phần trong StudyPlan có được khởi tạo đúng không
        onView(withId(R.id.lv)).check(matches(isDisplayed()));  // Kiểm tra ListView
        onView(withId(R.id.sp)).check(matches(isDisplayed()));  // Kiểm tra Spinner
        onView(withId(R.id.txtUpdateDate)).check(matches(isDisplayed()));  // Kiểm tra TextView ngày cập nhật
    }

    @Test
    public void testSpinnerAdapterInStudyPlan() {
        // Nhập thông tin đăng nhập đúng
        onView(withId(R.id.editUsername)).perform(typeText("vmq"), closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(typeText("123"), closeSoftKeyboard());

        // Nhấn nút Đăng nhập
        onView(withId(R.id.btnLogin)).perform(click());

        // Tiếp tục nhấn vào biểu tượng kế hoạch học tập (StudyPlan)
        onView(withId(R.id.btnStudyplane)).perform(click());

        // Kiểm tra số lượng mục trong Spinner (8 học kỳ)
        onView(withId(R.id.sp)).check(matches(isDisplayed()));
        // Giả sử bạn muốn kiểm tra số lượng mục trong Spinner, bạn có thể thêm đoạn mã tùy chỉnh để kiểm tra số lượng mục.
    }

    @Test
    public void testUpdateDateInStudyPlan() {
        // Nhập thông tin đăng nhập đúng
        onView(withId(R.id.editUsername)).perform(typeText("vmq"), closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(typeText("123"), closeSoftKeyboard());

        // Nhấn nút Đăng nhập
        onView(withId(R.id.btnLogin)).perform(click());

        // Tiếp tục nhấn vào biểu tượng kế hoạch học tập (StudyPlan)
        onView(withId(R.id.btnStudyplane)).perform(click());

        // Kiểm tra ngày cập nhật có hiển thị đúng hay không
        onView(withId(R.id.txtUpdateDate)).check(matches(isDisplayed()));
    }

    @Test
    public void testListViewPopulationInStudyPlan() {
        // Nhập thông tin đăng nhập đúng
        onView(withId(R.id.editUsername)).perform(typeText("vmq"), closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(typeText("123"), closeSoftKeyboard());

        // Nhấn nút Đăng nhập
        onView(withId(R.id.btnLogin)).perform(click());

        // Tiếp tục nhấn vào biểu tượng kế hoạch học tập (StudyPlan)
        onView(withId(R.id.btnStudyplane)).perform(click());

        // Kiểm tra ListView có được hiển thị đúng không
        onView(withId(R.id.lv)).check(matches(isDisplayed()));
    }
}