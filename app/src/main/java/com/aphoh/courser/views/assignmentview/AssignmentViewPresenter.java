package com.aphoh.courser.views.assignmentview;

import android.os.Bundle;

import com.aphoh.courser.base.BasePresenter;
import com.aphoh.courser.db.DataInteractor.Assignment;
import com.aphoh.courser.db.DataInteractor.Student;
import com.aphoh.courser.db.DataInteractor.Submission;
import com.aphoh.courser.db.DateUtils;
import com.aphoh.courser.views.assignmentview.AssignmentViewView.ResponseModel;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Will on 9/8/15.
 */
public class AssignmentViewPresenter extends BasePresenter<AssignmentViewView> {

    int FETCH = 0;
    private long assignmentId;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        restartableLatestCache(FETCH,
                new Func0<Observable<List<ResponseModel>>>() {
                    @Override
                    public Observable<List<ResponseModel>> call() {
                        return Observable.combineLatest(getDataInteractor().getStudents(),
                                getDataInteractor().getAssignmentWithId(assignmentId),
                                new Func2<List<Student>, Assignment, List<ResponseModel>>() {
                                    @Override
                                    public List<ResponseModel> call(List<Student> students, Assignment assignment) {
                                        List<ResponseModel> models = new ArrayList<>(students.size());
                                        for (Student student : students) {
                                            ResponseModel model = new ResponseModel();
                                            model.student = student;
                                            model.dueDate = DateUtils.getDate(assignment.getIsoDueDate());
                                            models.add(model);
                                        }
                                        return models;
                                    }
                                })
                                .map(new Func1<List<ResponseModel>, List<ResponseModel>>() {
                                    @Override
                                    public List<ResponseModel> call(List<ResponseModel> responseModels) {
                                        for (ResponseModel model : responseModels) {
                                            List<Submission> submissions = getDataInteractor().getSubmissionsForStudent(model.getStudent().getId())
                                                    .toBlocking()
                                                    .first();
                                            if (submissions.size() > 0) {
                                                DateUtils.sortYoungestFirst(submissions, new DateUtils.DateTimeGetter<Submission>() {
                                                    @Override
                                                    public DateTime getDateTime(Submission item) {
                                                        return DateUtils.getDate(item.getIsoDate());
                                                    }
                                                });
                                                DateTime time = DateUtils.getDate(submissions.get(0).getIsoDate());
                                                model.submissionStatus =
                                                        "Submitted " +
                                                                DateUtils.getTimeUntilDate(time);
                                            } else {
                                                model.submissionStatus = "Not submitted";
                                            }
                                        }
                                        return responseModels;
                                    }
                                })
                                .observeOn(getScheduler());
                    }
                },
                new Action2<AssignmentViewView, List<ResponseModel>>() {
                    @Override
                    public void call(AssignmentViewView assignmentViewView, List<ResponseModel> responseModels) {
                        assignmentViewView.publishItems(responseModels);
                    }
                },
                new Action2<AssignmentViewView, Throwable>() {
                    @Override
                    public void call(AssignmentViewView assignmentViewView, Throwable throwable) {
                        assignmentViewView.publishError(throwable);
                    }
                });
    }

    public void request(long assignmentId) {
        this.assignmentId = assignmentId;
        start(FETCH);
    }
}
