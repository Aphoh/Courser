package com.aphoh.courser.db;

import com.aphoh.courser.model.Assignment;
import com.aphoh.courser.model.Course;

import java.util.List;

import rx.Observable;

/**
 * Created by Will on 9/5/15.
 */
public interface DataInteractor {
    Observable<Assignment> createAssignment(String title, long courseId);

    Observable<Course> createCourse(String name, String term, int year);

    Observable<List<Assignment>> getAssignmentsForCourse(long courseId);

    Observable<List<Course>> getCourses();
}
