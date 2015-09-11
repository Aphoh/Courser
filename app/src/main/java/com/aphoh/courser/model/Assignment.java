package com.aphoh.courser.model;

import com.aphoh.courser.db.DateUtils;
import com.orm.SugarRecord;

/**
 * Created by Will on 9/4/15.
 */
public class Assignment extends SugarRecord<Assignment> {
    String title;
    String isoDueDate;
    Course course;

    public Assignment() {
    }

    public Assignment(String title, Course course) {
        this.title = title;
        this.course = course;
        isoDueDate = DateUtils.toString(DateUtils.getNow());
    }

    public String getTitle() {
        return title;
    }

    public Course getCourse() {
        return course;
    }

    public String getIsoDueDate() {
        return isoDueDate;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "title='" + title + '\'' +
                ", course=" + course +
                '}';
    }
}
