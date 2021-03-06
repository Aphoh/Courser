package com.aphoh.courser.views.assignments;

import android.os.Bundle;

import com.aphoh.courser.base.BasePresenter;
import com.aphoh.courser.db.DataInteractor.Assignment;

import java.util.List;

import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;

/**
 * Created by Will on 9/5/15.
 */
public class AssignmentsPresenter extends BasePresenter<AssignmentsView> {

    int ASSIGNMENTS_FETCH = 0;
    int ASSIGNMENT_CREATE = 1;
    long courseId;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        restartableLatestCache(ASSIGNMENTS_FETCH,
                new Func0<Observable<List<Assignment>>>() {
                    @Override
                    public Observable<List<Assignment>> call() {
                        return getDataInteractor().getAssignmentsForCourse(courseId)
                                .observeOn(getScheduler());
                    }
                },
                new Action2<AssignmentsView, List<Assignment>>() {
                    @Override
                    public void call(AssignmentsView assignmentsView, List<Assignment> assignments) {
                        assignmentsView.publishItems(assignments);
                    }
                },
                new Action2<AssignmentsView, Throwable>() {
                    @Override
                    public void call(AssignmentsView assignmentsView, Throwable throwable) {
                        assignmentsView.onError(throwable);
                    }
                });
    }

    public void requestAssignments(final long courseId){
        this.courseId = courseId;
        start(ASSIGNMENTS_FETCH);
    }

    public void requestCreateAssignment(final long courseId, final String name) {
        restartableFirst(ASSIGNMENT_CREATE,
                new Func0<Observable<Assignment>>() {
                    @Override
                    public Observable<Assignment> call() {
                        return getDataInteractor().createAssignment(name, courseId)
                                .observeOn(getScheduler());
                    }
                },
                new Action2<AssignmentsView, Assignment>() {
                    @Override
                    public void call(AssignmentsView assignmentsView, Assignment assignment) {
                        start(ASSIGNMENTS_FETCH);
                    }
                },
                new Action2<AssignmentsView, Throwable>() {
                    @Override
                    public void call(AssignmentsView assignmentsView, Throwable throwable) {
                        assignmentsView.onError(throwable);
                    }
                });
        start(ASSIGNMENT_CREATE);
    }
}
