package com.aphoh.courser.test;

import com.aphoh.courser.App;
import com.aphoh.courser.BuildConfig;
import com.aphoh.courser.TestApp;
import com.aphoh.courser.base.DaggerAppComponent;
import com.aphoh.courser.db.DataInteractor;
import com.aphoh.courser.utils.MockDataInteractor;
import com.aphoh.courser.utils.MockDataModule;
import com.aphoh.courser.utils.MockSchedulerModule;
import com.aphoh.courser.utils.model.MockAssignment;
import com.aphoh.courser.utils.model.MockCourse;
import com.aphoh.courser.utils.model.MockStudent;
import com.aphoh.courser.utils.model.MockSubmission;
import com.aphoh.courser.views.assignmentview.AssignmentViewPresenter;
import com.aphoh.courser.views.assignmentview.AssignmentViewView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import rx.schedulers.TestScheduler;

import static com.aphoh.courser.views.assignmentview.AssignmentViewView.*;
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
    MockDataModule dataModule = new MockDataModule(dataInteractor);

    @Before
    public void setUp() throws Exception {
        dataInteractor.clear();
    }

    @Test
    public void testSingleItem() throws Exception {
        createTestScheduler();
        App.setAppComponent(
                DaggerAppComponent.builder()
                        .dataModule(dataModule)
                        .schedulerModule(new MockSchedulerModule(testScheduler))
                        .build()
        );

        DataInteractor.Course course = new MockCourse(MOCK_ID, "mock", "mock", 0);
        DataInteractor.Student student = new MockStudent(MOCK_ID, "mock", 0);
        DataInteractor.Assignment assignment = new MockAssignment(MOCK_ID, "mock", course, "mock");
        MockSubmission submission = new MockSubmission(MOCK_ID, student, assignment);
        dataInteractor.createCourse(course);
        dataInteractor.createAssignment(assignment);
        dataInteractor.createStudent(student);
        dataInteractor.createSubmission(submission);

        AssignmentViewPresenter presenter = new AssignmentViewPresenter();
        MockAssignmentViewView assignmentViewView = new MockAssignmentViewView();
        presenter.takeView(assignmentViewView);

        testScheduler.triggerActions();

        List<ResponseModel> results = assignmentViewView.getModels();
        assertThat(results).hasSize(1);
    }

    private void createTestScheduler(){
        testScheduler = new TestScheduler();
    }

    class MockAssignmentViewView implements AssignmentViewView{
        List<ResponseModel> models;

        @Override
        public void publishItems(List<ResponseModel> responseModels) {
            models = responseModels;
        }

        @Override
        public long getAssignmentId() {
            return MOCK_ID;
        }

        public List<ResponseModel> getModels() {
            return models;
        }
    }

}
