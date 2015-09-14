package com.aphoh.courser.base;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Will on 9/10/15.
 */
@Module
public class SchedulerModule {

    @Provides
    public Scheduler provideSubscriptionScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
