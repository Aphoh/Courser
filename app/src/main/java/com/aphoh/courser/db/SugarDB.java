package com.aphoh.courser.db;

import android.util.Log;

import com.aphoh.courser.model.Assignment;
import com.aphoh.courser.model.Course;
import com.aphoh.courser.util.LogUtil;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Will on 9/4/15.
 */
public class SugarDB implements DataInteractor {

    LogUtil log = new LogUtil(SugarDB.class.getSimpleName());

    //CREATORS

    @Override
    public Observable<Assignment> createAssignment(final String title, final long courseId) {
        return Observable.create(new Observable.OnSubscribe<Assignment>() {
            @Override
            public void call(Subscriber<? super Assignment> subscriber) {
                Assignment assignment = new Assignment(title, Course.findById(Course.class, courseId));
                assignment.save();
                log.d("Done");
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
                log.d("Done");
                subscriber.onNext(course);
            }
        });
    }

    //GET ALL

    @Override
    public Observable<List<Assignment>> getAssignmentsForCourse(final long courseId) {
        return Observable.create(new Observable.OnSubscribe<List<Assignment>>() {
            @Override
            public void call(Subscriber<? super List<Assignment>> subscriber) {
                List<Assignment> assignments = Assignment.find(Assignment.class, "course = ?", Long.valueOf(courseId).toString());
                log.d("Done");
                subscriber.onNext(assignments);
            }
        });
    }

    @Override
    public Observable<List<Course>> getCourses() {
        return Observable.just(Course.listAll(Course.class));
    }

    //DELETION


    @Override
    public Observable<Course> deleteCourse(final long id) {
        return Observable.create(new Observable.OnSubscribe<Course>() {
            @Override
            public void call(Subscriber<? super Course> subscriber) {
                Course course = Course.findById(Course.class, id);
                course.delete();
                subscriber.onNext(course);
            }
        });
    }
}
