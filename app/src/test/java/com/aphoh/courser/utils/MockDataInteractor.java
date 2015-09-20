package com.aphoh.courser.utils;

import com.aphoh.courser.db.DataInteractor;
import com.aphoh.courser.db.DateUtils;
import com.aphoh.courser.utils.model.MockAssignment;
import com.aphoh.courser.utils.model.MockCourse;
import com.aphoh.courser.utils.model.MockStudent;
import com.aphoh.courser.utils.model.MockSubmission;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

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
        return getCourseWithId(courseId)
                .map(new Func1<Course, Assignment>() {
                    @Override
                    public Assignment call(Course course) {
                        Assignment assignment = MockAssignment.newInstance(title, course, DateUtils.toString(DateTime.now()));
                        assignments.add(assignment);
                        return assignment;
                    }
                });
    }

    @Override
    public Observable<Course> createCourse(final String name, final String term, final int year) {
        return Observable.create(new OnSubscribe<Course>() {
            @Override
            public void call(Subscriber<? super Course> subscriber) {
                Course course = MockCourse.newInstance(name, term, year);
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
                Student student = MockStudent.newInstance(name, age);
                students.add(student);
                subscriber.onNext(student);
            }
        });
    }

    @Override
    public Observable<Submission> createSubmission(final long assignmentId, final long studentId) {
        return Observable.zip(getAssignmentWithId(assignmentId), getStudentWithId(studentId),
                new Func2<Assignment, Student, Submission>() {
                    @Override
                    public Submission call(Assignment assignment, Student student) {
                        Submission submission = MockSubmission.newInstance(student, assignment);
                        submissions.add(submission);
                        return submission;
                    }
                });
    }

    @Override
    public Observable<List<Assignment>> getAssignmentsForCourse(final long courseId) {
        return Observable.create(new OnSubscribe<List<Assignment>>() {
            @Override
            public void call(Subscriber<? super List<Assignment>> subscriber) {
                List<Assignment> result = new ArrayList<>();
                for (Assignment assignment : assignments) {
                    if (assignment.getCourse().getId() == courseId) result.add(assignment);
                }
                subscriber.onNext(result);
            }
        });
    }

    @Override
    public Observable<Assignment> getAssignmentWithId(final long id) {
        return Observable.create(new OnSubscribe<Assignment>() {
            @Override
            public void call(Subscriber<? super Assignment> subscriber) {
                for (Assignment assignment : assignments) {
                    if (assignment.getId() == id) subscriber.onNext(assignment);
                }
            }
        });
    }

    @Override
    public Observable<Course> getCourseWithId(final long id) {
        return Observable.create(new OnSubscribe<Course>() {
            @Override
            public void call(Subscriber<? super Course> subscriber) {
                for (Course course : courses) {
                    if (course.getId() == id) subscriber.onNext(course);
                }

            }
        });
    }

    @Override
    public Observable<Student> getStudentWithId(final long id) {
        return Observable.create(new OnSubscribe<Student>() {
            @Override
            public void call(Subscriber<? super Student> subscriber) {
                for (Student student : students) {
                    if (student.getId() == id) subscriber.onNext(student);
                }
            }
        });
    }

    @Override
    public Observable<List<Submission>> getSubmissionsForStudent(final long studentId) {
        return Observable.create(new OnSubscribe<List<Submission>>() {
            @Override
            public void call(Subscriber<? super List<Submission>> subscriber) {
                List<Submission> subs = new ArrayList<>();
                for (Submission submission : submissions) {
                    if (submission.getStudent().getId() == studentId) subs.add(submission);
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
        return getCourseWithId(id)
                .map(new Func1<Course, Course>() {
                    @Override
                    public Course call(Course course) {
                        courses.remove(course);
                        return course;
                    }
                });
    }

    public void clear() {
        assignments = new ArrayList<>();
        courses = new ArrayList<>();
        students = new ArrayList<>();
        submissions = new ArrayList<>();
    }

    public void createCourse(Course course) {
        courses.add(course);
    }

    public void createStudent(Student student) {
        students.add(student);
    }

    public void createAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public void createSubmission(Submission submission) {
        submissions.add(submission);
    }
}
