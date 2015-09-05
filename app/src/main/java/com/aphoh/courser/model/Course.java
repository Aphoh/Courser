package com.aphoh.courser.model;

import com.orm.SugarRecord;

/**
 * Created by Will on 9/4/15.
 */
public class Course extends SugarRecord<Course> {
    String name;
    String term;
    int year;

    public Course() {
    }

    public Course(String name, String term, int year) {
        this.name = name;
        this.term = term;
        this.year = year;
    }
}
