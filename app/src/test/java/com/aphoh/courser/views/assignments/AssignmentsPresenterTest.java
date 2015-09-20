package com.aphoh.courser.views.assignments;

import com.aphoh.courser.BuildConfig;
import com.aphoh.courser.TestApp;
import com.aphoh.courser.base.DaggerAppComponent;
import com.aphoh.courser.base.Injector;
import com.aphoh.courser.db.DataInteractor;
import com.aphoh.courser.db.DataInteractor.Assignment;
import com.aphoh.courser.db.DateUtils;
import com.aphoh.courser.utils.MockDataInteractor;
import com.aphoh.courser.utils.MockDataModule;
import com.aphoh.courser.utils.MockSchedulerModule;
import com.aphoh.courser.utils.model.MockAssignment;
import com.aphoh.courser.utils.model.MockCourse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import rx.schedulers.TestScheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static com.aphoh.courser.utils.CustomMatchers.assertThat;

/**
 * Created by Will on 9/19/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApp.class, sdk = 21)
public class AssignmentsPresenterTest {

    TestScheduler testScheduler;
    MockDataInteractor dataInteractor = new MockDataInteractor();
    MockDataModule dataModule = new MockDataModule(dataInteractor);

    AssignmentsPresenter presenter;
    MockAssignmentsView mockAssignmentsView;

    @Test
    public void testEmpty() throws Exception {
        init();

        presenter.requestAssignments(-1);

        trigger();

        List<Assignment> result = mockAssignmentsView.getAssignments();
        assertThat(result).isEmpty();
    }

    @Test
    public void testSingleAssignment() throws Exception {
        init();

        int mockCourseId = 1;
        int mockAssignmentId = 3;
        MockCourse course = new MockCourse(mockCourseId, "mock", "mock", 0);
        MockAssignment as = new MockAssignment(mockAssignmentId, "mock", course, DateUtils.toString(DateUtils.getNow()));
        dataInteractor.createCourse(course);
        dataInteractor.createAssignment(as);

        presenter.requestAssignments(mockCourseId);

        trigger();

        List<Assignment> assignments = mockAssignmentsView.getAssignments();
        assertThat(assignments).hasSize(1);
        assertThat(assignments.get(0)).hasId(mockAssignmentId).hasTitle("mock");
    }

    @Test
    public void testAssignmentCreation() throws Exception {
        init();

        MockCourse course = MockCourse.newInstance("mock", "mock", 0);
        dataInteractor.createCourse(course);

        presenter.requestCreateAssignment(course.getId(), "mock");

        trigger();

        List<Assignment> assignments = dataInteractor.getAssignmentsForCourse(course.getId()).toBlocking().first();
        assertThat(assignments).hasSize(1);
        Assignment as = assignments.get(0);
        assertThat(as).hasTitle("mock");
        assertThat(as.getCourse()).isEqualTo(course);
    }

    private void init() {
        testScheduler = new TestScheduler();
        dataInteractor.clear();

        Injector.set(
                DaggerAppComponent.builder()
                        .dataModule(dataModule)
                        .schedulerModule(new MockSchedulerModule(testScheduler))
                        .build()
        );

        presenter = new AssignmentsPresenter();
        presenter.onCreate(null);

        mockAssignmentsView = new MockAssignmentsView();
    }

    private void trigger() {
        presenter.takeView(mockAssignmentsView);
        testScheduler.triggerActions();
    }

    class MockAssignmentsView implements AssignmentsView{
        List<Assignment> assignments;
        Throwable error;

        @Override
        public void publishItems(List<Assignment> assignments) {
            this.assignments = assignments;
        }

        @Override
        public void onError(Throwable throwable) {
            this.error = throwable;
        }

        public List<Assignment> getAssignments() {
            return assignments;
        }

        public Throwable getError() {
            return error;
        }
    }
}
