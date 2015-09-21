package com.aphoh.courser.rx;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;
import android.util.Log;

import rx.plugins.RxJavaPlugins;

/**
 * Created by Will on 9/20/15.
 */
public class RxAndroidJUnitRunner extends AndroidJUnitRunner{

    @Override
    public void onCreate(Bundle arguments) {
        RxJavaPlugins.getInstance().registerObservableExecutionHook(RxIdlingResource.get());

        RxIdlingResource.setLogLevel(RxIdlingResource.LogLevel.VERBOSE);

        Log.d("TESTING", "TESTS STARTING");

        super.onCreate(arguments);
    }
}
