package com.aphoh.courser.db;

import com.aphoh.courser.model.SugarAssignment;
import com.aphoh.courser.model.SugarCourse;
import com.aphoh.courser.model.SugarStudent;
import com.aphoh.courser.model.SugarSubmission;
import com.aphoh.courser.util.LogUtil;

import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

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
                SugarAssignment assignment = new SugarAssignment(title, SugarCourse.findById(SugarCourse.class, courseId));
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
                SugarCourse course = new SugarCourse(name, term, year);
                course.save();
                subscriber.onNext(course);
            }
        });
    }

    @Override
    public Observable<Submission> createSubmission(final long assignmentId, final long studentId) {
        return Observable.create(new Observable.OnSubscribe<Submission>() {
            @Override
            public void call(Subscriber<? super Submission> subscriber) {
                SugarSubmission submission = new SugarSubmission(
                        SugarStudent.findById(SugarStudent.class, studentId),
                        SugarAssignment.findById(SugarAssignment.class, assignmentId),
                        DateUtils.toString(DateTime.now())
                );
                submission.save();
                subscriber.onNext(submission);
            }
        });
    }

    @Override
    public Observable<Student> createStudent(final String name, final int age) {
        return Observable.create(new Observable.OnSubscribe<Student>() {
            @Override
            public void call(Subscriber<? super Student> subscriber) {
                SugarStudent student = new SugarStudent(name, age);
                student.save();
                subscriber.onNext(student);
            }
        });
    }


    //GET ALL

    private Student getStudentSynchronous(long studentId){
        return SugarStudent.findById(SugarStudent.class, studentId);
    }

    private Assignment getAssignmentSynchonous(long assignmentId){
        return SugarAssignment.findById(SugarAssignment.class, assignmentId);
    }

    @Override
    public Observable<List<Assignment>> getAssignmentsForCourse(final long courseId) {
        return Observable.create(new Observable.OnSubscribe<List<Assignment>>() {
            @Override
            public void call(Subscriber<? super List<Assignment>> subscriber) {
                List<SugarAssignment> assignments = SugarAssignment.find(SugarAssignment.class, "course = ?", Long.valueOf(courseId).toString());
                subscriber.onNext(Arrays.asList(
                        assignments.toArray(new Assignment[assignments.size()])
                ));
            }
        });
    }


    @Override
    public Observable<Assignment> getAssignmentWithId(final long id) {
        return Observable.just(getAssignmentSynchonous(id));
    }

    @Override
    public Observable<Course> getCourseWithId(long id) {
        return Observable.just((Course)SugarCourse.findById(SugarCourse.class, id));
    }

    @Override
    public Observable<Student> getStudentWithId(long id) {
        return Observable.just(getStudentSynchronous(id));
    }

    @Override
    public Observable<List<Submission>> getSubmissionsForStudent(final long studentId) {
        return Observable.create(new Observable.OnSubscribe<List<Submission>>() {
            @Override
            public void call(Subscriber<? super List<Submission>> subscriber) {
                List<SugarSubmission> sugarSubmissions = SugarSubmission.find(SugarSubmission.class, "student = ?", Long.valueOf(studentId).toString());
                subscriber.onNext(Arrays.asList(
                        sugarSubmissions.toArray(new Submission[sugarSubmissions.size()])
                ));
            }
        });
    }

    @Override
    public Observable<List<Course>> getCourses() {
        return Observable.create(new Observable.OnSubscribe<List<Course>>() {
            @Override
            public void call(Subscriber<? super List<Course>> subscriber) {
                List<SugarCourse> sugarCourses = SugarCourse.listAll(SugarCourse.class);
                subscriber.onNext(Arrays.asList(
                        sugarCourses.toArray(new Course[sugarCourses.size()])
                ));
            }
        });
    }

    @Override
    public Observable<List<Student>> getStudents() {
        return Observable.create(new Observable.OnSubscribe<List<Student>>() {
            @Override
            public void call(Subscriber<? super List<Student>> subscriber) {
                List<SugarStudent> sugarStudents = SugarStudent.listAll(SugarStudent.class);
                subscriber.onNext(Arrays.asList(
                                sugarStudents.toArray(new Student[sugarStudents.size()]))
                );
            }
        });
    }




    //DELETION


    @Override
    public Observable<Course> deleteCourse(final long id) {
        return Observable.create(new Observable.OnSubscribe<Course>() {
            @Override
            public void call(Subscriber<? super Course> subscriber) {
                SugarCourse course = SugarCourse.findById(SugarCourse.class, id);
                course.delete();
                subscriber.onNext(course);
            }
        });
    }
}
