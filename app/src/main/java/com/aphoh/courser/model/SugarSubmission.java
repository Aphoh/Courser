package com.aphoh.courser.model;

import com.aphoh.courser.db.DataInteractor;
import com.aphoh.courser.db.DataInteractor.Assignment;
import com.aphoh.courser.db.DataInteractor.Student;
import com.orm.SugarRecord;

/**
 * Created by Will on 9/8/15.
 */
public class SugarSubmission extends SugarRecord<SugarSubmission> implements DataInteractor.Submission{
    Student student;
    SugarAssignment assignment;
    String isoDate;


    public SugarSubmission() {
        super();
    }

    public SugarSubmission(Student student, SugarAssignment assignment, String isoDate) {
        this.student = student;
        this.assignment = assignment;
        this.isoDate = isoDate;
    }

    public Student getStudent() {
        return student;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public String getIsoDate() {
        return isoDate;
    }
}
