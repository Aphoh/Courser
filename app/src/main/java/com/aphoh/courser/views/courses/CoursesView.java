package com.aphoh.courser.views.courses;

import com.aphoh.courser.model.Course;

import java.util.List;

/**
 * Created by Will on 9/5/15.
 */
public interface CoursesView {
    void publishItems(List<Course> courseList);

    void onError(Throwable error);
}
