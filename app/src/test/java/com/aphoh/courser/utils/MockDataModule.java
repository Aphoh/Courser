package com.aphoh.courser.utils;

import com.aphoh.courser.base.DataModule;
import com.aphoh.courser.db.DataInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Will on 9/5/15.
 */
@Module
public class MockDataModule extends DataModule{

    DataInteractor interactor;

    public MockDataModule(DataInteractor interactor) {
        this.interactor = interactor;
    }

    @Provides
    @Singleton
    public DataInteractor provideDataInteractor() {
        return interactor;
    }

}
