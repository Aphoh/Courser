package com.aphoh.courser.model;

import com.orm.SugarRecord;

/**
 * Created by Will on 9/4/15.
 */
public class Assignment extends SugarRecord<Assignment> {
    String title;
    Course course;

    public Assignment() {
    }

    public Assignment(String title, Course course) {
        this.title = title;
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "title='" + title + '\'' +
                ", course=" + course +
                '}';
    }
}
