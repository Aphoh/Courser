package com.aphoh.courser.views.assignments;

import com.aphoh.courser.db.DataInteractor.Assignment;

import java.util.List;

/**
 * Created by Will on 9/5/15.
 */
public interface AssignmentsView {

    long getCourseId();

    void publishItems(List<Assignment> assignments);

    void onError(Throwable throwable);
}
