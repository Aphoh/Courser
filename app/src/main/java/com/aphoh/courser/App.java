package com.aphoh.courser;

import com.aphoh.courser.base.AppComponent;
import com.aphoh.courser.base.DaggerAppComponent;
import com.aphoh.courser.base.DataModule;
import com.aphoh.courser.base.Injector;
import com.aphoh.courser.base.SchedulerModule;
import com.facebook.stetho.Stetho;
import com.orm.SugarApp;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Will on 9/4/15.
 */
public class App extends SugarApp {


    @Override
    public void onCreate() {
        super.onCreate();
        Injector.set(DaggerAppComponent.builder()
                .dataModule(new DataModule())
                .schedulerModule(new SchedulerModule())
                .build());
        JodaTimeAndroid.init(this);
        if (BuildConfig.DEBUG && !isUnitTesting())
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                            .build()
            );

    }

    protected boolean isUnitTesting() {
        return false;
    }
}
