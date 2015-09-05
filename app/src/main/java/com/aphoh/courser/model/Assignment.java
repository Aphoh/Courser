package com.aphoh.courser.model;

import com.orm.SugarRecord;

/**
 * Created by Will on 9/4/15.
 */
public class Assignment extends SugarRecord<Assignment>{
    String title;
    Course course;

    public Assignment() {
    }

    public Assignment(String title, Course course) {
        this.title = title;
        this.course = course;
    }
}
