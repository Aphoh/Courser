package com.aphoh.courser.model;

import com.aphoh.courser.db.DataInteractor;
import com.aphoh.courser.db.DataInteractor.Course;
import com.aphoh.courser.db.DateUtils;
import com.orm.SugarRecord;

/**
 * Created by Will on 9/4/15.
 */
public class SugarAssignment extends SugarRecord<SugarAssignment> implements DataInteractor.Assignment {
    String title;
    String isoDueDate;
    SugarCourse course;

    public SugarAssignment() {

    }

    public SugarAssignment(String title, SugarCourse course) {
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
        return "SugarAssignment{" +
                "title='" + title + '\'' +
                ", course=" + course +
                '}';
    }
}
