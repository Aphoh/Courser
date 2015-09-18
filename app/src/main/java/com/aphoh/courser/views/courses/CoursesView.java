package com.aphoh.courser.views.courses;

import com.aphoh.courser.db.DataInteractor;
import com.aphoh.courser.db.DataInteractor.Course;
import com.aphoh.courser.db.DataInteractor.Student;

import java.util.List;

/**
 * Created by Will on 9/5/15.
 */
public interface CoursesView {
    void publishCourses(List<Course> courseList);

    void publishStudents(List<Student> students);

    void onError(Throwable error);
}
