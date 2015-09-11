package com.aphoh.courser.model;

import com.orm.SugarRecord;

/**
 * Created by Will on 9/8/15.
 */
public class Submission extends SugarRecord<Submission> {
    Student student;
    Assignment assignment;
    String isoDate;


    public Submission() {
        super();
    }

    public Submission(Student student, Assignment assignment, String isoDate) {
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
