package com.aphoh.courser.base;

import com.aphoh.courser.db.DataInteractor;
import com.aphoh.courser.db.SugarDB;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Will on 9/5/15.
 */
@Module
public class DataModule {

    @Provides
    @Singleton
    public DataInteractor provideDataInteractor() {
        return new SugarDB();
    }

}
