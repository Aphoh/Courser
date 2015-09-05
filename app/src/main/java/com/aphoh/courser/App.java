package com.aphoh.courser;

import android.content.Context;

import com.aphoh.courser.base.AppComponent;
import com.aphoh.courser.base.DaggerAppComponent;
import com.aphoh.courser.base.DataModule;
import com.orm.SugarApp;

/**
 * Created by Will on 9/4/15.
 */
public class App extends SugarApp {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .dataModule(new DataModule())
                .build();
    }

    public static AppComponent getAppComponent(){
        return component;
    }
}
