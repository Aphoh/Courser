package com.aphoh.courser.utils.model;

import com.aphoh.courser.db.DataInteractor;
import com.aphoh.courser.db.DateUtils;

import org.joda.time.DateTime;

/**
 * Created by Will on 9/13/15.
 */
public class MockSubmission implements DataInteractor.Submission {
    private static int idCounter = 1;
    long id;
    DataInteractor.Student student;
    DataInteractor.Assignment assignment;
    String date;

    public MockSubmission(long id, DataInteractor.Student student, DataInteractor.Assignment assignment) {
        this.id = id;
        this.student = student;
        this.assignment = assignment;
        date = DateUtils.toString(DateTime.now());
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public DataInteractor.Student getStudent() {
        return student;
    }

    @Override
    public DataInteractor.Assignment getAssignment() {
        return assignment;
    }

    @Override
    public String getIsoDate() {
        return date;
    }

    public static MockSubmission newInstance(DataInteractor.Student student, DataInteractor.Assignment assignment) {
        MockSubmission submission = new MockSubmission(idCounter, student, assignment);
        idCounter++;
        return submission;
    }
}
