package com.aphoh.courser.views.courses;

import com.aphoh.courser.base.DaggerAppComponent;
import com.aphoh.courser.base.Injector;
import com.aphoh.courser.db.DataInteractor.Course;
import com.aphoh.courser.db.DataInteractor.Student;
import com.aphoh.courser.utils.MockDataInteractor;
import com.aphoh.courser.utils.MockDataModule;
import com.aphoh.courser.utils.MockSchedulerModule;
import test.model.MockCourse;
import test.model.MockStudent;

import org.junit.Test;

import java.util.List;

import rx.schedulers.TestScheduler;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Will on 9/13/15.
 */
/*@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApp.class, sdk = 21)*/
public class CoursesPresenterTest {
    final int MOCK_ID = 1;

    TestScheduler testScheduler;
    MockDataInteractor dataInteractor = new MockDataInteractor();
    MockDataModule dataModule = new MockDataModule(dataInteractor);

    CoursesPresenter presenter;
    MockCoursesView mockCoursesView;

    private void init() {
        testScheduler = new TestScheduler();

        Injector.set(DaggerAppComponent.builder()
                .dataModule(dataModule)
                .schedulerModule(new MockSchedulerModule(testScheduler))
                .build());

        dataInteractor.clear();

        presenter = new CoursesPresenter();
        presenter.onCreate(null);

        mockCoursesView = new MockCoursesView();
    }

    private void trigger(){
        presenter.takeView(mockCoursesView);
        testScheduler.triggerActions();
    }

    @Test
    public void testEmpty() throws Exception {
        init();

        presenter.requestCourses();
        presenter.requestStudents();

        trigger();

        List<Course> courses = mockCoursesView.getCourses();
        List<Student> students = mockCoursesView.getStudents();
        assertThat(courses).isEmpty();
        assertThat(students).isEmpty();
    }

    @Test
    public void testSingleCourse() throws Exception {
        init();

        MockCourse course = new MockCourse(MOCK_ID, "mock", "mock", 0);
        dataInteractor.createCourse(course);

        presenter.requestCourses();

        trigger();

        assertThat(mockCoursesView.getCourses()).containsOnly(course);
    }

    @Test
    public void testSingleStudent() throws Exception {
        init();

        MockStudent student = new MockStudent(MOCK_ID, "mock", 0);
        dataInteractor.createStudent(student);

        presenter.requestStudents();

        trigger();

        assertThat(mockCoursesView.getStudents()).containsOnly(student);
    }

    @Test
    public void testCourseCreation() throws Exception {
        init();

        int mockAge = 20;
        presenter.requestCourseCreation("mock", "mock", mockAge);

        trigger();

        List<Course> courses = dataInteractor.getCourses().toBlocking().first();
        assertThat(courses).hasSize(1);

        Course course = courses.get(0);
        assertThat(course.getName()).isEqualTo("mock");
        assertThat(course.getTerm()).isEqualTo("mock");
        assertThat(course.getYear()).isEqualTo(mockAge);
    }

    @Test
    public void testStudentCreation() throws Exception {
        init();

        int mockAge = 20;
        presenter.requestStudentCreation("mockName", mockAge);

        trigger();

        List<Student> students = dataInteractor.getStudents().toBlocking().first();
        assertThat(students).hasSize(1);

        Student student = students.get(0);
        assertThat(student.getName()).isEqualTo("mockName");
        assertThat(student.getAge()).isEqualTo(mockAge);
    }


    class MockCoursesView implements CoursesView {
        List<Course> items;
        List<Student> students;
        Throwable error;

        @Override
        public void publishCourses(List<Course> courseList) {
            this.items = courseList;
        }

        @Override
        public void publishStudents(List<Student> students) {
            this.students = students;
        }

        @Override
        public void onError(Throwable error) {

        }

        public List<Course> getCourses() {
            return items;
        }

        public List<Student> getStudents() {
            return students;
        }


    }
}
