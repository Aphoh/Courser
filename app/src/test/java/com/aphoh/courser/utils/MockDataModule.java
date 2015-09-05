package com.aphoh.courser.utils;

import com.aphoh.courser.base.DataModule;
import com.aphoh.courser.db.DataInteractor;

import dagger.Module;

/**
 * Created by Will on 9/5/15.
 */
@Module
public class MockDataModule extends DataModule{

    @Override
    public DataInteractor provideDataInteractor() {
        return new MockDataInteractor();
    }

}
