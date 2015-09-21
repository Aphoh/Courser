package com.aphoh.courser.views.courses;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.aphoh.courser.R;
import com.aphoh.courser.base.DaggerAppComponent;
import com.aphoh.courser.base.Injector;
import com.aphoh.courser.base.SchedulerModule;
import com.aphoh.courser.db.DataInteractor;
import com.aphoh.courser.utils.MockDataInteractor;
import com.aphoh.courser.utils.MockDataModule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import test.model.MockCourse;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.aphoh.courser.db.DataInteractor.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * Created by Will on 9/20/15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CoursesActivityTest {

    DataInteractor interactor = Injector.get().interactor();

    @Rule
    public ActivityTestRule<CoursesActivity> rule = new ActivityTestRule<>(CoursesActivity.class);

    @Before
    public void setUp() throws Exception {
        interactor.deleteAll().subscribe();
    }

    @Test
    public void testEmpty(){
        runOnUiThread(rule, new Runnable() {
            @Override
            public void run() {
                rule.getActivity().publishCourses(new ArrayList<Course>());
            }
        });

        onView(withId(R.id.placeholder))
                .check(matches(withText(R.string.no_data)))
                .check(matches(isCompletelyDisplayed()));

        runOnUiThread(rule, new Runnable() {
            @Override
            public void run() {
                rule.getActivity().publishStudents(new ArrayList<Student>());
            }
        });

        onView(withId(R.id.placeholder))
                .check(matches(withText(R.string.no_data)))
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void testSingleCourse(){
        final String mockName = "mockName", mockTerm = "mockTerm";

        runOnUiThread(rule, new Runnable() {
            @Override
            public void run() {
                rule.getActivity().publishCourses(Arrays.asList(
                        (Course) MockCourse.newInstance(mockName, mockTerm, 0)
                ));
            }
        });

        onView(withId(R.id.activity_courses_recyclerview))
                .check(matches(hasDescendant(withText(mockName))))
                .check(matches(hasDescendant(withText(mockTerm))));
    }

    @Test
    public void testCourseCreation(){
        onView(withId(R.id.menu_courses_add))
                .perform(click());

        onView(withHint("Name"))
                .perform(click())
                .perform(typeTextIntoFocusedView("mock"));

        onView(withText("OK"))
                .perform(click());
    }

    public static void runOnUiThread(ActivityTestRule rule, Runnable runnable){
        try{
            rule.runOnUiThread(runnable);
        } catch (Throwable e){
            Log.e("TESTING", "Error running on UI thread: ", e);
        }
    }
}
