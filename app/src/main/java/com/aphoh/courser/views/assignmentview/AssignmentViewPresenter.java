package com.aphoh.courser.views.assignmentview;

import android.os.Bundle;

import com.aphoh.courser.base.BasePresenter;
import com.aphoh.courser.db.DateUtils;
import com.aphoh.courser.model.Assignment;
import com.aphoh.courser.model.Student;
import com.aphoh.courser.model.Submission;
import com.aphoh.courser.views.assignmentview.AssignmentViewView.ResponseModel;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Will on 9/8/15.
 */
public class AssignmentViewPresenter extends BasePresenter<AssignmentViewView> {

    int FETCH = 0;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    @Override
    protected void onTakeView(AssignmentViewView assignmentViewView) {
        super.onTakeView(assignmentViewView);
        final long assignmentId = assignmentViewView.getAssignmentId();
        restartableLatestCache(FETCH,
                new Func0<Observable<List<ResponseModel>>>() {
                    @Override
                    public Observable<List<ResponseModel>> call() {
                        return Observable.combineLatest(getDataInteractor().getStudents(),
                                getDataInteractor().getAssignmentWithId(assignmentId),
                                new Func2<List<Student>, Assignment, List<ResponseModel>>() {
                                    @Override
                                    public List<ResponseModel> call(List<Student> students, Assignment assignment) {
                                        List<ResponseModel> models = new ArrayList<ResponseModel>(students.size());
                                        DateTime dateTime = DateUtils.getDate(assignment.getIsoDueDate());
                                        for (Student student : students) {
                                            ResponseModel model = new ResponseModel();
                                            model.student = student;
                                            model.dueDate = dateTime;
                                            models.add(model);
                                        }
                                        return models;
                                    }
                                })
                                .flatMap(new Func1<List<ResponseModel>, Observable<ResponseModel>>() {
                                    @Override
                                    public Observable<ResponseModel> call(List<ResponseModel> responseModels) {
                                        return Observable.from(responseModels);
                                    }
                                })
                                .flatMap(new Func1<ResponseModel, Observable<ResponseModel>>() {
                                    @Override
                                    public Observable<ResponseModel> call(ResponseModel responseModel) {
                                        return Observable.combineLatest(
                                                Observable.just(responseModel),
                                                getDataInteractor().getSubmissionsForStudent(responseModel.student.getId()),
                                                new Func2<ResponseModel, List<Submission>, ResponseModel>() {
                                                    @Override
                                                    public ResponseModel call(ResponseModel responseModel, List<Submission> submissions) {
                                                        String submissionStatus;
                                                        if (submissions.size() > 0) {
                                                            DateUtils.sortYoungestFirst(submissions, new DateUtils.DateTimeGetter<Submission>() {
                                                                @Override
                                                                public DateTime getDateTime(Submission item) {
                                                                    return DateUtils.getDate(item.getIsoDate());
                                                                }
                                                            });
                                                            submissionStatus =
                                                                    "Submitted " +
                                                                    DateUtils.getTimeUntilDate(submissions.get(0).getIsoDate());
                                                        } else {
                                                            submissionStatus = "Not submitted";
                                                        }
                                                        responseModel.submissionStatus = submissionStatus;
                                                        return responseModel;
                                                    }
                                                });
                                    }
                                })
                                .toList()
                                .observeOn(getScheduler())
                                .subscribeOn(Schedulers.io());

                    }
                },
                new Action2<AssignmentViewView, List<ResponseModel>>() {
                    @Override
                    public void call(AssignmentViewView assignmentViewView, List<ResponseModel> responseModels) {
                        assignmentViewView.publishItems(responseModels);
                    }
                });
    }
}
