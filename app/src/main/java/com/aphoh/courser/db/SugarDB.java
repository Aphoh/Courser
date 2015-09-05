package com.aphoh.courser.db;

import com.aphoh.courser.model.Assignment;
import com.aphoh.courser.model.Course;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Will on 9/4/15.
 */
public class SugarDB implements DataInteractor {

    //CREATORS

    @Override
    public Observable<Assignment> createAssignment(final String title, final long courseId) {
        return Observable.create(new Observable.OnSubscribe<Assignment>() {
            @Override
            public void call(Subscriber<? super Assignment> subscriber) {
                Assignment assignment = new Assignment(title, Course.findById(Course.class, courseId));
                assignment.save();
                subscriber.onNext(assignment);
            }
        });
    }

    @Override
    public Observable<Course> createCourse(final String name, final String term, final int year) {
        return Observable.create(new Observable.OnSubscribe<Course>() {
            @Override
            public void call(Subscriber<? super Course> subscriber) {
                Course course = new Course(name, term, year);
                course.save();
                subscriber.onNext(course);
            }
        });
    }

    //Relational methods

    @Override
    public Observable<List<Assignment>> getAssignmentsForCourse(long courseId) {
        return Observable.just(Assignment.find(Assignment.class, "course = ?", Long.valueOf(courseId).toString()))
                .subscribeOn(Schedulers.io());
    }

    //GET ALL

    @Override
    public Observable<List<Course>> getCourses() {
        return Observable.just(Course.listAll(Course.class));
    }
}
