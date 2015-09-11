package com.aphoh.courser.utils;

import com.aphoh.courser.db.DataInteractor;
import com.aphoh.courser.db.DateUtils;
import com.aphoh.courser.model.Assignment;
import com.aphoh.courser.model.Course;
import com.aphoh.courser.model.Student;
import com.aphoh.courser.model.Submission;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

/**
 * Created by Will on 9/5/15.
 */
public class MockDataInteractor implements DataInteractor {
    List<Course> courses = new ArrayList<>();
    List<Assignment> assignments = new ArrayList<>();
    List<Student> students = new ArrayList<>();
    List<Submission> submissions = new ArrayList<>();

    @Override
    public Observable<Assignment> createAssignment(final String title, final long courseId) {
        return Observable.create(new OnSubscribe<Assignment>() {
            @Override
            public void call(Subscriber<? super Assignment> subscriber) {
                Assignment as = new Assignment(title, getRecordWithId(courses, courseId));
                assignments.add(as);
                subscriber.onNext(as);
            }
        });
    }

    @Override
    public Observable<Course> createCourse(final String name, final String term, final int year) {
        return Observable.create(new OnSubscribe<Course>() {
            @Override
            public void call(Subscriber<? super Course> subscriber) {
                Course course = new Course(name, term, year);
                courses.add(course);
                subscriber.onNext(course);
            }
        });
    }

    @Override
    public Observable<Student> createStudent(final String name, final int age) {
        return Observable.create(new OnSubscribe<Student>() {
            @Override
            public void call(Subscriber<? super Student> subscriber) {
                Student student = new Student(name, age);
                students.add(student);
                subscriber.onNext(student);
            }
        });
    }

    @Override
    public Observable<Submission> createSubmission(final long assignmentId, final long studentId) {
        return Observable.create(new OnSubscribe<Submission>() {
            @Override
            public void call(Subscriber<? super Submission> subscriber) {
                Submission submission = new Submission(getRecordWithId(students, studentId),
                        getRecordWithId(assignments, assignmentId),
                        DateUtils.toString(DateUtils.getNow()));
                submissions.add(submission);
                subscriber.onNext(submission);
            }
        });
    }

    @Override
    public Observable<List<Assignment>> getAssignmentsForCourse(final long courseId) {
        return Observable.create(new OnSubscribe<List<Assignment>>() {
            @Override
            public void call(Subscriber<? super List<Assignment>> subscriber) {
                List<Assignment> assignments = new ArrayList<>();
                for(Assignment assignment : assignments){
                    if(assignment.getCourse().getId() == courseId) assignments.add(assignment);
                }
                subscriber.onNext(assignments);
            }
        });
    }

    @Override
    public Observable<Assignment> getAssignmentWithId(long id) {
        return Observable.just(getRecordWithId(assignments, id));
    }

    @Override
    public Observable<List<Submission>> getSubmissionsForStudent(final long studentId) {
        return Observable.create(new OnSubscribe<List<Submission>>() {
            @Override
            public void call(Subscriber<? super List<Submission>> subscriber) {
                List<Submission> subs = new ArrayList<>();
                for(Submission submission : submissions){
                    if(submission.getStudent().getId() == studentId)  subs.add(submission);
                }
                subscriber.onNext(subs);
            }
        });
    }

    @Override
    public Observable<List<Course>> getCourses() {
        return Observable.just(courses);
    }

    @Override
    public Observable<List<Student>> getStudents() {
        return Observable.just(students);
    }

    @Override
    public Observable<Course> deleteCourse(final long id) {
        return Observable.create(new OnSubscribe<Course>() {
            @Override
            public void call(Subscriber<? super Course> subscriber) {
                Course course = getRecordWithId(courses, id);
                courses.remove(course);
                subscriber.onNext(course);
            }
        });
    }

    protected <T extends SugarRecord<T>> T getRecordWithId(List<T> records, long id){
        for(T record : records){
            if(record.getId() == id) return record;
        }
        return null;
    }
}
