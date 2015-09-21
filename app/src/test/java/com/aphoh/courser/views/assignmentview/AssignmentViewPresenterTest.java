package com.aphoh.courser.views.assignmentview;

import com.aphoh.courser.BuildConfig;
import com.aphoh.courser.TestApp;
import com.aphoh.courser.base.DaggerAppComponent;
import com.aphoh.courser.base.Injector;
import com.aphoh.courser.db.DataInteractor.Assignment;
import com.aphoh.courser.db.DataInteractor.Course;
import com.aphoh.courser.db.DataInteractor.Student;
import com.aphoh.courser.db.DateUtils;
import com.aphoh.courser.utils.MockDataInteractor;
import com.aphoh.courser.utils.MockDataModule;
import com.aphoh.courser.utils.MockSchedulerModule;
import test.model.MockAssignment;
import test.model.MockCourse;
import test.model.MockStudent;
import test.model.MockSubmission;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import rx.schedulers.TestScheduler;

import static com.aphoh.courser.views.assignmentview.AssignmentViewView.ResponseModel;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Will on 9/10/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApp.class, sdk = 21)
public class AssignmentViewPresenterTest {

    final int MOCK_ID = 1;

    TestScheduler testScheduler;
    MockDataInteractor dataInteractor = new MockDataInteractor();

    @Before
    public void setUp() throws Exception {
        dataInteractor.clear();
    }

    @Test
    public void testSingleItem() throws Exception {
        testScheduler = new TestScheduler();
        Injector.set(
                DaggerAppComponent.builder()
                        .dataModule(new MockDataModule(dataInteractor))
                        .schedulerModule(new MockSchedulerModule(testScheduler))
                        .build()
        );

        Course course = new MockCourse(MOCK_ID, "mock", "mock", 0);
        Student student = new MockStudent(MOCK_ID, "mock", 0);
        Assignment assignment = new MockAssignment(MOCK_ID, "mock", course, DateUtils.toString(DateTime.now()));
        MockSubmission submission = new MockSubmission(MOCK_ID, student, assignment);
        dataInteractor.createCourse(course);
        dataInteractor.createAssignment(assignment);
        dataInteractor.createStudent(student);
        dataInteractor.createSubmission(submission);

        AssignmentViewPresenter presenter = new AssignmentViewPresenter();
        presenter.onCreate(null);
        presenter.request(assignment.getId());

        MockAssignmentViewView assignmentViewView = new MockAssignmentViewView();
        presenter.takeView(assignmentViewView);

        testScheduler.triggerActions();

        assertThat(assignmentViewView.error).isNull();

        List<ResponseModel> results = assignmentViewView.getModels();
        assertThat(results).hasSize(1);

        ResponseModel model = results.get(0);
        assertThat(model.getStudent()).isEqualTo(student);
        assertThat(model.getSubmissionStatus()).isEqualTo(DateUtils.getTimeUntilDate(submission.getIsoDate()));
    }

    class MockAssignmentViewView implements AssignmentViewView {
        List<ResponseModel> models;
        Throwable error;

        @Override
        public void publishItems(List<ResponseModel> responseModels) {
            models = responseModels;
        }

        @Override
        public void publishError(Throwable error) {
            this.error = error;
        }

        public List<ResponseModel> getModels() {
            return models;
        }
    }

}
