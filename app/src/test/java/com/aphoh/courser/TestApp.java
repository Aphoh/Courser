package com.aphoh.courser;

import android.app.Application;

import com.aphoh.courser.base.AppComponent;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Will on 9/10/15.
 */
public class TestApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
