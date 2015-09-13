package com.aphoh.courser.views.courses;

import android.os.Bundle;

import com.aphoh.courser.base.BasePresenter;
import com.aphoh.courser.db.DataInteractor.Course;

import java.util.List;

import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;

/**
 * Created by Will on 9/4/15.
 */
public class CoursesPresenter extends BasePresenter<CoursesView> {

    int ALL_COURSES = 0;
    int CREATE_COURSE = 1;
    int DELETE_COURSE = 2;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    @Override
    protected void onTakeView(CoursesView coursesView) {
        super.onTakeView(coursesView);
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
                        coursesView.publishItems(courses);
                    }
                }, new Action2<CoursesView, Throwable>() {
                    @Override
                    public void call(CoursesView coursesView, Throwable throwable) {
                        coursesView.onError(throwable);
                    }
                });
        start(ALL_COURSES);
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


}
