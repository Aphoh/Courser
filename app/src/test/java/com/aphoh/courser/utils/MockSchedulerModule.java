package com.aphoh.courser.utils;

import com.aphoh.courser.base.SchedulerModule;

import javax.inject.Singleton;

import dagger.Provides;
import rx.Scheduler;

/**
 * Created by Will on 9/10/15.
 */
public class MockSchedulerModule extends SchedulerModule {

    Scheduler scheduler;

    public MockSchedulerModule(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    @Provides
    @Singleton
    public Scheduler provideSubscriptionScheduler() {
        return scheduler;
    }
}
