package com.aphoh.courser.db;

import android.util.Log;

import com.aphoh.courser.model.Assignment;
import com.aphoh.courser.model.Course;
import com.aphoh.courser.model.Student;
import com.aphoh.courser.model.Submission;
import com.aphoh.courser.util.LogUtil;

import org.joda.time.DateTime;

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

    @Override
    public Observable<Submission> createSubmission(final long assignmentId, final long studentId) {
        return Observable.create(new Observable.OnSubscribe<Submission>() {
            @Override
            public void call(Subscriber<? super Submission> subscriber) {
                Submission submission = new Submission(
                        getStudentSynchronous(studentId),
                        getAssignmentSynchonous(assignmentId),
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
                Student student = new Student(name, age);
                student.save();
                subscriber.onNext(student);
            }
        });
    }


    //GET ALL

    public Observable<Student> getStudent(long studentId){
        return Observable.just(getStudentSynchronous(studentId));
    }

    private Student getStudentSynchronous(long studentId){
        return Student.findById(Student.class, studentId);
    }

    public Observable<Assignment> getAssignment(long assignmentId){
        return Observable.just(getAssignmentSynchonous(assignmentId));
    }

    private Assignment getAssignmentSynchonous(long assignmentId){
        return Assignment.findById(Assignment.class, assignmentId);
    }

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

    @Override
    public Observable<List<Student>> getStudents() {
        return Observable.just(Student.listAll(Student.class));
    }

    @Override
    public Observable<Assignment> getAssignmentWithId(long id) {
        return null;
    }

    @Override
    public Observable<List<Submission>> getSubmissionsForStudent(long studentId) {
        return null;
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
