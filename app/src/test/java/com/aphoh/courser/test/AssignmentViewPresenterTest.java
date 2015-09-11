package com.aphoh.courser.test;

import com.aphoh.courser.App;
import com.aphoh.courser.BuildConfig;
import com.aphoh.courser.TestApp;
import com.aphoh.courser.base.DaggerAppComponent;
import com.aphoh.courser.base.DataModule;
import com.aphoh.courser.db.DataInteractor;
import com.aphoh.courser.db.DateUtils;
import com.aphoh.courser.model.Assignment;
import com.aphoh.courser.model.Course;
import com.aphoh.courser.model.Student;
import com.aphoh.courser.model.Submission;
import com.aphoh.courser.utils.MockDataInteractor;
import com.aphoh.courser.utils.MockDataModule;
import com.aphoh.courser.utils.MockSchedulerModule;
import com.aphoh.courser.views.assignmentview.AssignmentViewPresenter;
import com.aphoh.courser.views.assignmentview.AssignmentViewView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import dagger.Provides;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.TestScheduler;

import static com.aphoh.courser.views.assignmentview.AssignmentViewView.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Will on 9/10/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApp.class)
public class AssignmentViewPresenterTest {
    final int ASSIGNMENT_ID = 0;

    TestScheduler testScheduler;
    DataInteractor dataInteractor;
    MockDataModule dataModule = new MockDataModule();

    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
        App.setAppComponent(
                DaggerAppComponent.builder()
                        .dataModule(dataModule)
                        .schedulerModule(new MockSchedulerModule(testScheduler))
                        .build()
        );
        dataInteractor = App.getAppComponent().interactor();
    }

    @Test
    public void testSingleItem() throws Exception {
        dataInteractor.createAssignment("mock", 0);

        AssignmentViewPresenter presenter = new AssignmentViewPresenter();
        MockAssignmentViewView assignmentViewView = new MockAssignmentViewView();
        presenter.takeView(assignmentViewView);

        testScheduler.triggerActions();

        List<ResponseModel> results = assignmentViewView.getModels();
        assertThat(results).hasSize(1);

    }

    class MockAssignmentViewView implements AssignmentViewView{
        List<ResponseModel> models;

        @Override
        public void publishItems(List<ResponseModel> responseModels) {
            models = responseModels;
        }

        @Override
        public long getAssignmentId() {
            return ASSIGNMENT_ID;
        }

        public List<ResponseModel> getModels() {
            return models;
        }
    }
}
