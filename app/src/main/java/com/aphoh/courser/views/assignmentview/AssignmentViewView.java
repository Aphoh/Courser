package com.aphoh.courser.views.assignmentview;

import com.aphoh.courser.db.DataInteractor.Student;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Will on 9/8/15.
 */
public interface AssignmentViewView {

    void publishItems(List<ResponseModel> responseModels);

    void publishError(Throwable error);

    class ResponseModel {
        public static final String STATUS_SUBMITTED = "SUBMITTED";
        public static final String STATUS_NOT_SUBMITTED = "NOT_SUBMITTED";

        Student student;
        String submissionStatus;

        public Student getStudent() {
            return student;
        }

        public String getSubmissionStatus() {
            return submissionStatus;
        }

        public void setStudent(Student student) {
            this.student = student;
        }

        public void setSubmissionStatus(String submissionStatus) {
            this.submissionStatus = submissionStatus;
        }
    }
}
