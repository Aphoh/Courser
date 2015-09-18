package com.aphoh.courser.views.courses;

import android.os.Bundle;

import com.aphoh.courser.base.BasePresenter;
import com.aphoh.courser.db.DataInteractor.Course;
import com.aphoh.courser.db.DataInteractor.Student;

import java.util.List;

import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;

/**
 * Created by Will on 9/4/15.
 */
public class CoursesPresenter extends BasePresenter<CoursesView> {

    int CREATE_STUDENT = 3;
    int ALL_COURSES = 0;
    int CREATE_COURSE = 1;
    int DELETE_COURSE = 2;
    int ALL_STUDENTS = 4;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        restartableLatestCache(ALL_COURSES,
                new Func0<Observable<List<Course>>>() {
                    @Override
                    public Observable<List<Course>> call() {
                        return getDataInteractor().getCourses()
                                .observeOn(getScheduler());
                    }
                },
                new Action2<CoursesView, List<Course>>() {
                    @Override
                    public void call(CoursesView coursesView, List<Course> courses) {
                        coursesView.publishCourses(courses);
                    }
                }, new Action2<CoursesView, Throwable>() {
                    @Override
                    public void call(CoursesView coursesView, Throwable throwable) {
                        coursesView.onError(throwable);
                    }
                });

        restartableLatestCache(ALL_STUDENTS,
                new Func0<Observable<List<Student>>>() {
                    @Override
                    public Observable<List<Student>> call() {
                        return getDataInteractor().getStudents()
                                .observeOn(getScheduler());
                    }
                },
                new Action2<CoursesView, List<Student>>() {
                    @Override
                    public void call(CoursesView coursesView, List<Student> students) {
                        coursesView.publishStudents(students);
                    }
                },
                new Action2<CoursesView, Throwable>() {
                    @Override
                    public void call(CoursesView coursesView, Throwable throwable) {
                        coursesView.onError(throwable);
                    }
                });
    }

    public void requestCourses() {
        start(ALL_COURSES);
    }

    public void requestStudents() {
        start(ALL_STUDENTS);
    }


    public void requestCourseCreation(final String title, final String term, final int year) {
        restartableFirst(CREATE_COURSE,
                new Func0<Observable<Course>>() {
                    @Override
                    public Observable<Course> call() {
                        return getDataInteractor().createCourse(title, term, year)
                                .observeOn(getScheduler());
                    }
                },
                new Action2<CoursesView, Course>() {
                    @Override
                    public void call(CoursesView coursesView, Course course) {
                        start(ALL_COURSES);
                    }
                },
                new Action2<CoursesView, Throwable>() {
                    @Override
                    public void call(CoursesView coursesView, Throwable throwable) {
                        coursesView.onError(throwable);
                    }
                });
        start(CREATE_COURSE);
    }

    public void requestCourseDeletion(final long courseId) {
        restartableFirst(DELETE_COURSE,
                new Func0<Observable<Course>>() {
                    @Override
                    public Observable<Course> call() {
                        return getDataInteractor().deleteCourse(courseId)
                                .observeOn(getScheduler());
                    }
                },
                new Action2<CoursesView, Course>() {
                    @Override
                    public void call(CoursesView coursesView, Course course) {
                        start(ALL_COURSES);
                    }
                },
                new Action2<CoursesView, Throwable>() {
                    @Override
                    public void call(CoursesView coursesView, Throwable throwable) {
                        coursesView.onError(throwable);
                    }
                });
        start(DELETE_COURSE);
    }


    public void requestStudentCreation(final String name, final int age) {
        restartableFirst(CREATE_STUDENT,
                new Func0<Observable<Student>>() {
                    @Override
                    public Observable<Student> call() {
                        return getDataInteractor().createStudent(name, age)
                                .observeOn(getScheduler());
                    }
                },
                new Action2<CoursesView, Student>() {
                    @Override
                    public void call(CoursesView coursesView, Student student) {
                        start(ALL_STUDENTS);
                    }
                },
                new Action2<CoursesView, Throwable>() {
                    @Override
                    public void call(CoursesView coursesView, Throwable throwable) {
                        coursesView.onError(throwable);
                    }
                });
        start(CREATE_STUDENT);
    }

}
