package com.aphoh.courser.utils;

import com.aphoh.courser.db.DataInteractor;
import com.aphoh.courser.model.Assignment;
import com.aphoh.courser.model.Course;
import com.aphoh.courser.model.Student;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Will on 9/5/15.
 */
public class MockDataInteractor implements DataInteractor {

    public static final Course mockCourse = new Course("mock", "summer", 2015);
    public static final Assignment mockAssignment = new Assignment("mock", mockCourse);
    public static final Student mockStudent = new Student("mock", 0);

    @Override
    public Observable<Assignment> createAssignment(String title, long courseId) {
        return Observable.create(new Observable.OnSubscribe<Assignment>() {
            @Override
            public void call(Subscriber<? super Assignment> subscriber) {
                subscriber.onNext(mockAssignment);
            }
        });
    }

    @Override
    public Observable<Course> createCourse(String name, String term, int year) {
        return Observable.create(new Observable.OnSubscribe<Course>() {
            @Override
            public void call(Subscriber<? super Course> subscriber) {
                subscriber.onNext(mockCourse);
            }
        });
    }

    @Override
    public Observable<List<Assignment>> getAssignmentsForCourse(long courseId) {
        return null;
    }

    @Override
    public Observable<List<Course>> getCourses() {
        return null;
    }
}
