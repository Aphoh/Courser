package com.aphoh.courser.utils.model;

import com.aphoh.courser.db.DataInteractor;

/**
 * Created by Will on 9/13/15.
 */
public class MockCourse implements DataInteractor.Course{
    private static int idCounter = 1;
    long id;
    String name;
    String term;
    int year;

    public MockCourse(long id, String name, String term, int year) {
        this.id = id;
        this.name = name;
        this.term = term;
        this.year = year;

    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTerm() {
        return term;
    }

    @Override
    public int getYear() {
        return year;
    }

    public static MockCourse newInstance(String name, String term, int year){
        MockCourse course = new MockCourse(idCounter, name, term, year);
        idCounter++;
        return course;
    }
}
