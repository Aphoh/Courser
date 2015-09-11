package com.aphoh.courser.db;

import com.aphoh.courser.model.Assignment;
import com.aphoh.courser.model.Course;
import com.aphoh.courser.model.Student;
import com.aphoh.courser.model.Submission;
import com.aphoh.courser.util.LogUtil;

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

    Observable<Assignment> getAssignmentWithId(long id);

    Observable<List<Submission>> getSubmissionsForStudent(long studentId);

    Observable<List<Course>> getCourses();

    Observable<List<Student>> getStudents();

    //DELETION
    Observable<Course> deleteCourse(long id);
}
