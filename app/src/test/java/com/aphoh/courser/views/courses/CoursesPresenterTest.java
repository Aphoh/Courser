package com.aphoh.courser.views.courses;

import com.aphoh.courser.App;
import com.aphoh.courser.BuildConfig;
import com.aphoh.courser.TestApp;
import com.aphoh.courser.base.DaggerAppComponent;
import com.aphoh.courser.db.DataInteractor;
import com.aphoh.courser.db.DataInteractor.Course;
import com.aphoh.courser.utils.MockDataInteractor;
import com.aphoh.courser.utils.MockDataModule;
import com.aphoh.courser.utils.MockSchedulerModule;
import com.aphoh.courser.utils.model.MockCourse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import rx.schedulers.TestScheduler;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Will on 9/13/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApp.class, sdk = 21)
public class CoursesPresenterTest {
    final int MOCK_ID = 1;

    TestScheduler testScheduler;
    MockDataInteractor dataInteractor = new MockDataInteractor();
    MockDataModule dataModule = new MockDataModule(dataInteractor);

    @Before
    public void setUp() throws Exception {
        dataInteractor.clear();
    }

    @Test
    public void testEmpty() throws Exception {
        init();

        CoursesPresenter presenter = new CoursesPresenter();
        presenter.onCreate(null);
        presenter.requestCourses();

        MockCoursesView mockCoursesView = new MockCoursesView();
        presenter.takeView(mockCoursesView);

        testScheduler.triggerActions();

        List<Course> results = mockCoursesView.getItems();
        assertThat(results).isEmpty();
    }

    @Test
    public void testSingle() throws Exception {
        init();

        MockCourse course = new MockCourse(MOCK_ID, "mock", "mock", 0);
        dataInteractor.createCourse(course);

        CoursesPresenter presenter = new CoursesPresenter();
        presenter.onCreate(null);
        presenter.requestCourses();

        MockCoursesView view = new MockCoursesView();
        presenter.takeView(view);

        testScheduler.triggerActions();

        assertThat(view.getItems()).containsOnly(course);
    }

    private void init(){
        testScheduler = new TestScheduler();
        App.setAppComponent(DaggerAppComponent.builder()
                .dataModule(dataModule)
                .schedulerModule(new MockSchedulerModule(testScheduler))
                .build());
    }

    class MockCoursesView implements CoursesView{
        List<Course> items;
        Throwable error;

        @Override
        public void publishItems(List<Course> courseList) {
            this.items = courseList;
        }

        @Override
        public void onError(Throwable error) {

        }

        public List<Course> getItems() {
            return items;
        }
    }
}
