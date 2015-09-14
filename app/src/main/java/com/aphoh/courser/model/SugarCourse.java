package com.aphoh.courser.model;

import com.aphoh.courser.db.DataInteractor;
import com.orm.SugarRecord;

/**
 * Created by Will on 9/4/15.
 */
public class SugarCourse extends SugarRecord<SugarCourse> implements DataInteractor.Course {
    String name;
    String term;
    int year;

    public SugarCourse() {
    }

    public SugarCourse(String name, String term, int year) {
        this.name = name;
        this.term = term;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public String getTerm() {
        return term;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "SugarCourse{" +
                "name='" + name + '\'' +
                ", term='" + term + '\'' +
                ", year=" + year +
                '}';
    }
}
