package com.aphoh.courser.base;

import android.os.Bundle;

import com.aphoh.courser.App;
import com.aphoh.courser.db.DataInteractor;

import nucleus.presenter.RxPresenter;
import rx.Scheduler;

/**
 * Created by Will on 9/5/15.
 */
public class BasePresenter<ViewType> extends RxPresenter<ViewType> {
    DataInteractor interactor = App.getAppComponent().interactor();
    Scheduler scheduler = App.getAppComponent().scheduler();

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

    }

    protected DataInteractor getDataInteractor() {
        return interactor;
    }

    protected Scheduler getScheduler(){
        return scheduler;
    }
}
