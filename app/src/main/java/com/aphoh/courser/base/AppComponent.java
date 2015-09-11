package com.aphoh.courser.base;

import com.aphoh.courser.db.DataInteractor;

import javax.inject.Singleton;

import dagger.Component;
import rx.Scheduler;

/**
 * Created by Will on 9/5/15.
 */
@Singleton
@Component(modules = {DataModule.class, SchedulerModule.class})
public interface AppComponent {
    DataInteractor interactor();
    Scheduler scheduler();
}
