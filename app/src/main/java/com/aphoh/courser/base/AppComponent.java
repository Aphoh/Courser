package com.aphoh.courser.base;

import com.aphoh.courser.db.DataInteractor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Will on 9/5/15.
 */
@Singleton
@Component(modules = DataModule.class)
public interface AppComponent {
    DataInteractor interactor();
}
