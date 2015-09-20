package com.aphoh.courser.db;

import java.util.List;

import rx.Observable;

/**
 * Created by Will on 9/5/15.
 */
public interface DataInteractor {

    //CREATION
    Observable<Assignment> createAssignment(String title, long courseId);

    Observable<Course> createCourse(String name, String term, int year);

    Observable<Student> createStudent(String name, int age);

    Observable<Submission> createSubmission(long assignmentId, long studentId);

    //GETTING
    Observable<List<Assignment>> getAssignmentsForCourse(long courseId);

    Observable<Course> getCourseWithId(long id);

    Observable<Assignment> getAssignmentWithId(long id);

    Observable<Student> getStudentWithId(long id);

    Observable<List<Submission>> getSubmissionsForStudent(long studentId);

    Observable<List<Course>> getCourses();

    Observable<List<Student>> getStudents();

    //DELETION
    Observable<Course> deleteCourse(long id);

    Observable<Void> deleteAll();

    //CLASSES

    interface BaseModel {
        Long getId();
    }

    interface Assignment {
        Long getId();

        String getTitle();

        String getIsoDueDate();

        Course getCourse();

    }

    interface Course {
        Long getId();

        String getName();

        String getTerm();

        int getYear();
    }

    interface Student {
        Long getId();

        String getName();

        int getAge();
    }

    interface Submission {
        Long getId();

        Student getStudent();

        Assignment getAssignment();

        String getIsoDate();
    }
}
