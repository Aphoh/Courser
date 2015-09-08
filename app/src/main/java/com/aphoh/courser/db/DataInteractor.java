package com.aphoh.courser.db;

import com.aphoh.courser.model.Assignment;
import com.aphoh.courser.model.Course;
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

    //GETTING
    Observable<List<Assignment>> getAssignmentsForCourse(long courseId);

    Observable<List<Course>> getCourses();

    //DELETION
    Observable<Course> deleteCourse(long id);
}
