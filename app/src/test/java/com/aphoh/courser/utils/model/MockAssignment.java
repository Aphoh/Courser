package com.aphoh.courser.utils.model;

import com.aphoh.courser.db.DataInteractor;

/**
 * Created by Will on 9/13/15.
 */
public class MockAssignment implements DataInteractor.Assignment{
    private static int idCounter = 1;
    long id;
    DataInteractor.Course course;
    String title;
    String isoDate;

    public MockAssignment(long id, String title, DataInteractor.Course course, String isoDate) {
        this.id = id;
        this.course = course;
        this.isoDate = isoDate;
        this.title = title;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public DataInteractor.Course getCourse() {
        return course;
    }

    @Override
    public String getIsoDueDate() {
        return isoDate;
    }

    public static MockAssignment newInstance(String title, DataInteractor.Course course, String isoDate) {
        MockAssignment assignment = new MockAssignment(idCounter, title, course, isoDate);
        idCounter++;
        return assignment;
    }
}
